package com.spiralforge.cureme.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spiralforge.cureme.constants.ApiConstant;
import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.dto.AvailableSlotDto;
import com.spiralforge.cureme.dto.DoctorSlotResponseDto;
import com.spiralforge.cureme.dto.SlotDto;
import com.spiralforge.cureme.entity.Booking;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.exception.DoctorNotFoundException;
import com.spiralforge.cureme.repository.BookingRepository;
import com.spiralforge.cureme.repository.DoctorAvailabilityRepository;
import com.spiralforge.cureme.repository.DoctorRepository;
import com.spiralforge.cureme.repository.SlotRepository;

/**
 * @author Sujal.
 * @since 2020-02-12.
 */
@Service
public class SlotServiceImpl implements SlotService {

	@Autowired
	private SlotRepository slotRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorAvailabilityRepository doctorAvailabilityRepository;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SlotServiceImpl.class);

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method list of booked slots for a doctor are
	 *        fetched from database.
	 * 
	 * @param doctorId
	 * @return List of booked slots are fetched.
	 * 
	 */
	@Override
	public List<DoctorSlotResponseDto> getSlotsByDoctorId(Long doctorId) {
		logger.info("inside fetch slots based on doctor id");
		List<DoctorSlotResponseDto> doctorSlotResponseDtoList = new ArrayList<>();

		List<Booking> bookings = bookingRepository.getBookingByDoctorId(doctorId);

		bookings.forEach(booking -> {
			logger.info("slot size gretter than 0 ");

			DoctorSlotResponseDto doctorSlotResponseDto = new DoctorSlotResponseDto();
			BeanUtils.copyProperties(booking, doctorSlotResponseDto);
			BeanUtils.copyProperties(booking.getSlot(), doctorSlotResponseDto);
			BeanUtils.copyProperties(booking.getUser(), doctorSlotResponseDto);
			doctorSlotResponseDto.setSlotDate(booking.getSlot().getDoctorAvailability().getAvailableDate());
			doctorSlotResponseDtoList.add(doctorSlotResponseDto);
		});
		return doctorSlotResponseDtoList;
	}

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method add the slots for a doctor in database
	 * 
	 * @param doctorId
	 * @return Slot created status.
	 * @throws DoctorNotFoundException
	 */
	@Override
	public List<Slot> addSlots(@NotNull Long doctorId, @Valid AddSlotRequestDto addSlotRequestDto)
			throws DoctorNotFoundException {
		LocalDate start = addSlotRequestDto.getAvailableFromDate();
		LocalDate end = addSlotRequestDto.getAvailableToDate();
		List<Slot> slotList = new ArrayList<>();
		Optional<Doctor> doctor = doctorRepository.findById(doctorId);
		if (!doctor.isPresent()) {
			throw new DoctorNotFoundException(ApiConstant.DOCTOR_NOT_FOUND);
		} else {
			for (LocalDate date = start; date.isEqual(end) || date.isBefore(end); date = date.plusDays(1)) {
				logger.info("dates are found");
				DoctorAvailability doctorAvailability = saveDoctorAvailability(date, addSlotRequestDto, doctor.get());
				List<Slot> slots = createSlotForDoctorAvailability(doctorAvailability);
				slotList.addAll(slots);
			}
		}
		return slotList;
	}

	/**
	 * @author Sujal
	 * 
	 *         This method is used to save the doctor availability in the database
	 * @param date
	 * @param addSlotRequestDto
	 * @param doctor
	 * @return DoctorAvailability object
	 */
	@Transactional
	private DoctorAvailability saveDoctorAvailability(LocalDate date, @Valid AddSlotRequestDto addSlotRequestDto,
			Doctor doctor) {
		DoctorAvailability doctorAvailability = new DoctorAvailability();
		BeanUtils.copyProperties(addSlotRequestDto, doctorAvailability);
		doctorAvailability.setAvailableDate(date);
		doctorAvailability.setDoctor(doctor);
		doctorAvailability.setCreatedDate(LocalDateTime.now());
		doctorAvailability.setAvailableStatus(ApplicationConstants.AVAILABLE);
		return doctorAvailabilityRepository.save(doctorAvailability);
	}

	/**
	 * @author Sujal
	 * 
	 *         This method is used to save the slots for a doctor in the database
	 * @param doctorAvailability
	 * @return List<Slot> list of created slots
	 */
	@Transactional
	private List<Slot> createSlotForDoctorAvailability(DoctorAvailability doctorAvailability) {
		List<Slot> slots = new ArrayList<>();
		if (!Objects.isNull(doctorAvailability)) {
			LocalTime start = doctorAvailability.getAvailableFrom();
			LocalTime end = doctorAvailability.getAvailableTo();
			for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(15)) {
				logger.info("time slot are found");
				Slot slot = new Slot();
				slot.setDoctorAvailability(doctorAvailability);
				slot.setSlotTime(time);
				slots.add(slot);
			}
		}
		return slotRepository.saveAll(slots);
	}

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method fetch the available doctor slots for a
	 *        doctor in database
	 * 
	 * @param doctorId
	 * @return Slot created status.
	 * @throws DoctorNotFoundException
	 */
	@Override
	public List<AvailableSlotDto> getAvailableSlots(@NotNull Long doctorId) throws DoctorNotFoundException {
		Optional<Doctor> doctor = doctorRepository.findById(doctorId);
		List<AvailableSlotDto> availableSlotDtos = null;
		if (!doctor.isPresent()) {
			throw new DoctorNotFoundException(ApiConstant.DOCTOR_NOT_FOUND);
		} else {

			List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByDoctor(doctor.get());
			if (doctorAvailabilities.isEmpty()) {
				throw new DoctorNotFoundException(ApiConstant.DOCTOR_NOT_AVAILABLE);
			} else {
				logger.info("inside available slot are found");

				availableSlotDtos = doctorAvailabilities.stream().map(availableSlot -> {
					List<SlotDto> slotDtos = getSlotList(availableSlot);
					AvailableSlotDto availableSlotDto = new AvailableSlotDto();
					if (!slotDtos.isEmpty())
						availableSlotDto.setSlots(slotDtos);
					availableSlotDto.setSlotDate(availableSlot.getAvailableDate());
					return availableSlotDto;
				}).collect(Collectors.toList());
			}
		}
		return availableSlotDtos;
	}

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method fetch the available slots for a doctor in
	 *        database
	 * @param doctor
	 * @param availableSlot 
	 * @return List<SlotDto> is list of slots
	 */
	private List<SlotDto> getSlotList(DoctorAvailability availableSlot) {
		logger.info("inside slot list method");

		List<Slot> slots = slotRepository.getSlotsByDoctorAvailability(availableSlot);
		if (!slots.isEmpty()) {
			return slots.stream().map(slot -> {
				SlotDto slotDto = new SlotDto();
				BeanUtils.copyProperties(slot, slotDto);
				Optional<Booking> booking = bookingRepository.findBySlot(slot);
				if (booking.isPresent()
						&& booking.get().getBookingStatus().equalsIgnoreCase(ApplicationConstants.BOOKED))
					slotDto.setAvailableStatus(ApplicationConstants.BOOKED);
				else {
					slotDto.setAvailableStatus(ApplicationConstants.AVAILABLE);
				}
				return slotDto;
			}).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

}
