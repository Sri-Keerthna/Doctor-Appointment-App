package com.spiralforge.cureme.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.BookingRequestDto;
import com.spiralforge.cureme.dto.BookingResponseDto;
import com.spiralforge.cureme.dto.DoctorResponseDto;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Rating;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.exception.DoctorsNotFoundException;
import com.spiralforge.cureme.exception.LocationNotFoundException;
import com.spiralforge.cureme.exception.SlotNotFoundException;
import com.spiralforge.cureme.repository.BookingRepository;
import com.spiralforge.cureme.repository.DoctorAvailabilityRepository;
import com.spiralforge.cureme.repository.DoctorRepository;
import com.spiralforge.cureme.repository.RatingRepository;
import com.spiralforge.cureme.repository.SlotRepository;
import com.spiralforge.cureme.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestUserService {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	RatingRepository ratingRepository;

	@Mock
	DoctorRepository doctorRepository;

	@Mock
	DoctorAvailabilityRepository doctorAvailabilityRepository;

	@Mock
	SlotRepository slotRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	BookingRepository bookingRepository;

	List<DoctorResponseDto> responseList = new ArrayList<>();
	BookingResponseDto bookingResponseDto = new BookingResponseDto();
	BookingRequestDto bookingRequestDto = new BookingRequestDto();
	List<DoctorResponseDto> doctorResponseDtoList = new ArrayList<>();
	DoctorResponseDto doctorResponseDto = new DoctorResponseDto();

	List<DoctorAvailability> doctorAvailabilityList = new ArrayList<>();
	DoctorAvailability doctorAvailability = new DoctorAvailability();
	Doctor doctor = new Doctor();
	List<Doctor> doctors = new ArrayList<>();
	List<Rating> ratings = new ArrayList<>();
	Rating rating = new Rating();

	Slot slot = new Slot();

	@Before
	public void setUp() {

		doctor.setDoctorId(1L);
		doctor.setDoctorName("Sri");
		doctor.setDiseaseCure("cough");
		doctor.setExperience(3.5f);
		doctor.setQualification("MBBS");
		doctor.setSpecialization("ENT");
		doctors.add(doctor);

		doctorAvailability.setAvailableDate(LocalDate.of(2020, 02, 13));
		doctorAvailability.setDoctor(doctor);
		doctorAvailability.setLocation("Bangalore");
		doctorAvailability.setDoctorAvailabilityId(1L);
		doctorAvailabilityList.add(doctorAvailability);

		rating.setRatingId(1);
		rating.setRatingValue(4.5f);
		rating.setDoctor(doctor);
		ratings.add(rating);

		doctorResponseDto.setLocation(doctorAvailability.getLocation());
		BeanUtils.copyProperties(doctors, doctorResponseDto);
		doctorResponseDto.setRating(rating.getRatingValue());
		doctorResponseDto.setDiseaseCure(doctor.getDiseaseCure());
		doctorResponseDto.setDoctorName(doctor.getDoctorName());
		doctorResponseDto.setDoctorId(1L);
		doctorResponseDtoList.add(doctorResponseDto);

		slot.setDoctorAvailability(doctorAvailability);
		slot.setSlotId(1L);
		slot.setSlotTime(LocalTime.of(10, 10, 10, 00));

		bookingResponseDto.setStatusCode(ApplicationConstants.SUCCESS);
		bookingResponseDto.setMessage(ApplicationConstants.APPOINTMENT_SUCCESS);
	}

	@Test
	public void testGetDoctorsListPositve() throws LocationNotFoundException, DoctorsNotFoundException {
		Mockito.when(doctorAvailabilityRepository.findByLocation("Bangalore")).thenReturn(doctorAvailabilityList);
		Mockito.when(ratingRepository.findByDoctor(doctor)).thenReturn(ratings);
		Mockito.when(doctorRepository.findByDoctorId(rating.getDoctor().getDoctorId(), "Bangalore"))
				.thenReturn(doctors);
		List<DoctorResponseDto> responseList = userServiceImpl.getDoctorsList("Bangalore", "Bangalore");
		assertEquals(1, responseList.size());

	}

	@Test(expected = LocationNotFoundException.class)
	public void testGetDoctorsListNegatve() throws LocationNotFoundException, DoctorsNotFoundException {
		List<DoctorAvailability> doctorAvailabilityLists = new ArrayList<>();
		Mockito.when(doctorAvailabilityRepository.findByLocation("Bangalore")).thenReturn(doctorAvailabilityLists);
		userServiceImpl.getDoctorsList("Bangalore", "Bangalore");
	}

	@Test(expected = DoctorsNotFoundException.class)
	public void testGetDoctorsListNegatveException() throws LocationNotFoundException, DoctorsNotFoundException {
		List<Doctor> doctors = new ArrayList<>();
		Mockito.when(doctorAvailabilityRepository.findByLocation("Bangalore")).thenReturn(doctorAvailabilityList);
		Mockito.when(ratingRepository.findByDoctor(doctor)).thenReturn(ratings);
		Mockito.when(doctorRepository.findByDoctorId(rating.getDoctor().getDoctorId(), "Bangalore"))
				.thenReturn(doctors);
		userServiceImpl.getDoctorsList("Bangalore", "Bangalore");
	}

	@Test
	public void testBookSlotPositve() throws DoctorsNotFoundException, SlotNotFoundException {
		Mockito.when(slotRepository.findBySlotId(bookingRequestDto.getSlotId())).thenReturn(Optional.of(slot));
		bookingResponseDto = userServiceImpl.bookSlot(bookingRequestDto);
		assertEquals(ApplicationConstants.SUCCESS, bookingResponseDto.getStatusCode());
	}

	@Test(expected = SlotNotFoundException.class)
	public void testBookSlotNegativeSlotNotFoundException() throws DoctorsNotFoundException, SlotNotFoundException {
		Mockito.when(slotRepository.findBySlotId(2L)).thenReturn(Optional.of(slot));
		userServiceImpl.bookSlot(bookingRequestDto);
	}
}
