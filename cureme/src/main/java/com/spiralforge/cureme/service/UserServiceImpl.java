package com.spiralforge.cureme.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.BookingRequestDto;
import com.spiralforge.cureme.dto.BookingResponseDto;
import com.spiralforge.cureme.dto.DoctorResponseDto;
import com.spiralforge.cureme.dto.LoginRequestDto;
import com.spiralforge.cureme.entity.Booking;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Otp;
import com.spiralforge.cureme.entity.Rating;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.DoctorsNotFoundException;
import com.spiralforge.cureme.exception.LocationNotFoundException;
import com.spiralforge.cureme.exception.SlotNotFoundException;
import com.spiralforge.cureme.exception.UserNotFoundException;
import com.spiralforge.cureme.repository.BookingRepository;
import com.spiralforge.cureme.repository.DoctorAvailabilityRepository;
import com.spiralforge.cureme.repository.DoctorRepository;
import com.spiralforge.cureme.repository.OtpRepository;
import com.spiralforge.cureme.repository.RatingRepository;
import com.spiralforge.cureme.repository.SlotRepository;
import com.spiralforge.cureme.repository.UserRepository;
import com.spiralforge.cureme.smsservice.SmsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	DoctorAvailabilityRepository doctorAvailabilityRepository;

	@Autowired
	SlotRepository slotRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private SmsService smsService;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-11. In this method user will search doctor by giving location
	 *        and other filterations using search key.
	 * @param location  given by user.
	 * @param searchkey it can be doctor's specialization, qualification,
	 *                  diseaseCure and doctor name.
	 * @return list of doctors with rating.
	 * @throws LocationNotFoundException if in that location no doctors available.
	 * @throws DoctorsNotFoundException  if doctors have no availability.
	 * 
	 */
	@Override
	public List<DoctorResponseDto> getDoctorsList(String location, String searchkey)
			throws LocationNotFoundException, DoctorsNotFoundException {
		List<DoctorResponseDto> responseDto = new ArrayList<>();
		
		Set<Long> doctorSet= new HashSet<>();
		List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityRepository.findByLocation(location);
		if (doctorAvailabilityList.isEmpty()) {
			logger.error("Location not found");
			throw new LocationNotFoundException(ApplicationConstants.DOCTOR_LOCATION_NOT_FOUND_EXCEPTION);
		}
		doctorAvailabilityList.forEach(i->
			doctorSet.add(i.getDoctor().getDoctorId())	
		);
		List<Long> doctorList= new ArrayList<>(doctorSet);
			
			doctorList.forEach(doctorIndex -> {
			DoctorResponseDto doctorResponseDto= new DoctorResponseDto();
			Optional<Doctor> doctorResponse=doctorRepository.findById(doctorIndex);
			List<Rating> ratings = ratingRepository.findByDoctor(doctorResponse.get());
			doctorResponseDto.setLocation(location);
			ratings.forEach(rating -> {
				doctorResponseDto.setRating(rating.getRatingValue());
				List<Doctor> doctors = doctorRepository.findByDoctorId(rating.getDoctor().getDoctorId(), searchkey);
				doctors.forEach(list -> {
					BeanUtils.copyProperties(list, doctorResponseDto);
					responseDto.add(doctorResponseDto);
				});
			});
		});
		if (responseDto.isEmpty()) {
			logger.error("No doctors available");
			throw new DoctorsNotFoundException(ApplicationConstants.DOCTORS_NOT_FOUND_EXCEPTION);
		}
		logger.info("Got the list of doctors");
		return responseDto;
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-11. In this method user can book slot for a particular doctor
	 *        by giving some details.
	 * @param bookingRequestDto has slotId, slotTime, slotDate with doctorId.
	 * @return message will be sent if it is booked successfully.
	 * @throws DoctorsNotFoundException if the doctor is not available.
	 * @throws SlotNotFoundException    if slot is not available for that doctor.
	 * 
	 */
	@Transactional
	public BookingResponseDto bookSlot(BookingRequestDto bookingRequestDto)
			throws DoctorsNotFoundException, SlotNotFoundException {
		Optional<Slot> slots = slotRepository.findBySlotId(bookingRequestDto.getSlotId());
		if (!slots.isPresent()) {
			logger.error("Slot not available for this doctor");
			throw new SlotNotFoundException(ApplicationConstants.SLOT_NOT_FOUND_EXCEPTION);
		}
		User user = new User();
		Booking booking = new Booking();
		BeanUtils.copyProperties(bookingRequestDto, user);
		BeanUtils.copyProperties(bookingRequestDto, booking);
		booking.setBookingStatus(ApplicationConstants.BOOKED);
		booking.setSlot(slots.get());
		booking.setUser(user);
		bookingRepository.save(booking);
		userRepository.save(user);
		BookingResponseDto bookingResponseDto = new BookingResponseDto();
		bookingResponseDto.setStatusCode(ApplicationConstants.SUCCESS);
		bookingResponseDto.setMessage(ApplicationConstants.APPOINTMENT_SUCCESS);
		logger.info("Appointment is confirmed");
		return bookingResponseDto;
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used to check the mobile number is present or not
	 * 
	 * @param loginRequestDto which takes the input as mobile number
	 * @return ResponseDto return a success message
	 * @throws UserNotFoundException thrown when the mobile number is not found
	 */
	@Override
	public String checkUser(LoginRequestDto loginRequestDto) throws UserNotFoundException {
		Long mobileNumber = loginRequestDto.getMobileNumber();
		User user = userRepository.findByMobileNumber(mobileNumber);
		if (Objects.isNull(user)) {
			log.error(ApplicationConstants.USER_NOTFOUND_MESSAGE);
			throw new UserNotFoundException(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		}
		Integer otp = 10000 + new Random().nextInt(90000);
		smsService.sendSMS(mobileNumber, otp);
		Otp otpDetails = new Otp();
		otpDetails.setMobileNumber(mobileNumber);
		otpDetails.setOtpNumber(otp);
		otpDetails.setOtpStatus(ApplicationConstants.OTP_ACTIVE_MESSAGE);
		otpRepository.save(otpDetails);
		return ApplicationConstants.OTP_SENT_MESSAGE;
	}
}
