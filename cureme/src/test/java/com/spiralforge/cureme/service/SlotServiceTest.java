package com.spiralforge.cureme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.dto.AvailableSlotDto;
import com.spiralforge.cureme.dto.DoctorSlotResponseDto;
import com.spiralforge.cureme.dto.SlotDto;
import com.spiralforge.cureme.dto.SlotResponseDto;
import com.spiralforge.cureme.entity.Booking;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.DoctorNotFoundException;
import com.spiralforge.cureme.exception.UserNotFoundException;
import com.spiralforge.cureme.repository.BookingRepository;
import com.spiralforge.cureme.repository.DoctorAvailabilityRepository;
import com.spiralforge.cureme.repository.DoctorRepository;
import com.spiralforge.cureme.repository.SlotRepository;
import com.spiralforge.cureme.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SlotServiceTest {

	@InjectMocks
	private SlotServiceImpl slotServiceImpl;
	
	@Mock
	private SlotRepository slotRepository;

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private DoctorRepository doctorRepository;

	@Mock
	private DoctorAvailabilityRepository doctorAvailabilityRepository;

	SlotResponseDto slotResponseDto = null;
	DoctorSlotResponseDto doctorSlotResponseDto= null;
	List<DoctorSlotResponseDto> doctorSlotResponseDtos=null;
	User user = null;
	AddSlotRequestDto addSlotRequestDto =null;
	Slot slot=null;
	DoctorAvailability doctorAvailability=null;
	Doctor doctor=null;
	List<Slot> slots=null;
	SlotDto slotDto=null;
	AvailableSlotDto availableSlotDto=null;
	
	List<SlotDto> slotDtos=null;
	List<AvailableSlotDto> availableDtos=null;
	
	Booking booking=null;
	List<Booking> bookings=null;
	
	List<DoctorAvailability> doctorAvailabilities=null;

	@Before
	public void before() {
		slotResponseDto = new SlotResponseDto();
		user = new User();
		user.setUserId(1L);
		user.setMobileNumber(9876543210L);
		
		doctorSlotResponseDto=new DoctorSlotResponseDto();
		doctorSlotResponseDto.setBookingId(1L);
		doctorSlotResponseDto.setDisease("Fever");
		doctorSlotResponseDto.setEmailId("sujal@gmail.com");
		doctorSlotResponseDto.setSlotDate(LocalDate.now());
		doctorSlotResponseDto.setSlotId(1L);
		doctorSlotResponseDto.setSlotTime(LocalTime.now());
		
		doctorSlotResponseDtos= new ArrayList<>();
		doctorSlotResponseDtos.add(doctorSlotResponseDto);
		slotResponseDto.setSlots(doctorSlotResponseDtos);
		
		addSlotRequestDto = new AddSlotRequestDto();
		addSlotRequestDto.setAvailableFromDate(LocalDate.now());
		addSlotRequestDto.setAvailableToDate(LocalDate.now());
		addSlotRequestDto.setAvailableFrom(LocalTime.now());
		addSlotRequestDto.setAvailableTo(LocalTime.now());
		addSlotRequestDto.setLocation("Bangalore");
		
		doctor=new Doctor();
		doctor.setDoctorId(1L);
		doctor.setDiseaseCure("Fever");
		doctor.setDoctorName("Dr Sujal");
		doctor.setExperience(2F);
		doctor.setQualification("MBBS");
		doctor.setSpecialization("Heart");
		doctor.setUser(user);
		
		doctorAvailability= new DoctorAvailability();
		doctorAvailability.setAvailableDate(LocalDate.now());
		doctorAvailability.setAvailableStatus(ApplicationConstants.AVAILABLE);
		doctorAvailability.setAvailableFrom(LocalTime.now());
		doctorAvailability.setAvailableTo(LocalTime.now());
		doctorAvailability.setCreatedDate(LocalDateTime.now());
		doctorAvailability.setDoctor(doctor);
		doctorAvailability.setDoctorAvailabilityId(1L);
		doctorAvailability.setLocation("Bangalore");
		
		slot= new Slot();
		slot.setSlotId(1L);
		slot.setSlotTime(LocalTime.now());
		slot.setDoctorAvailability(doctorAvailability);
		
		slots=new ArrayList<>();
		slots.add(slot);
		
		slotDto=new SlotDto();
		slotDto.setAvailableStatus(ApplicationConstants.AVAILABLE);
		slotDto.setSlotTime(LocalTime.now());
		slotDto.setSlotId(1L);
		
		slotDtos=new ArrayList<>();
		slotDtos.add(slotDto);
		
		availableSlotDto=new AvailableSlotDto();
		availableSlotDto.setSlotDate(LocalDate.now());
		availableSlotDto.setSlots(slotDtos);
		
		availableDtos=new ArrayList<>();
		availableDtos.add(availableSlotDto);
		
		booking=new Booking();
		booking.setBookingId(1L);
		booking.setBookingStatus(ApplicationConstants.BOOKED);
		booking.setDisease("Fever");
		booking.setSlot(slot);
		booking.setUser(user);
		
		bookings=new ArrayList<>();
		bookings.add(booking);
		
		doctorAvailabilities=new ArrayList<>();
		doctorAvailabilities.add(doctorAvailability);
	}

	@Test
	public void testGetSlotsByDoctorIdpositive(){
		Long doctorId=1L;
		Mockito.when(bookingRepository.getBookingByDoctorId(doctorId)).thenReturn(bookings);
		List<DoctorSlotResponseDto> doctorSlotResponseDtos= slotServiceImpl.getSlotsByDoctorId(doctorId);
		assertEquals(1, doctorSlotResponseDtos.size());
	}
	
	@Test
	public void testGetSlotsByDoctorIdNegative(){
		Long doctorId=1L;
		Mockito.when(bookingRepository.getBookingByDoctorId(doctorId)).thenReturn(Collections.emptyList());
		List<DoctorSlotResponseDto> doctorSlotResponseDtos= slotServiceImpl.getSlotsByDoctorId(doctorId);
		assertEquals(0, doctorSlotResponseDtos.size());
	}
	

	@Test
	public void testAddSlotsByDoctorIdPositive() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorAvailabilityRepository.save(doctorAvailability)).thenReturn(doctorAvailability);
		Mockito.when(slotRepository.saveAll(slots)).thenReturn(slots);
		
		List<Slot> doctorSlotResponseDtos= slotServiceImpl.addSlots(doctorId, addSlotRequestDto);
		assertEquals(0, doctorSlotResponseDtos.size());
	}
	
	@Test
	public void testAddSlotsByDoctorIdNegative() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorAvailabilityRepository.save(doctorAvailability)).thenReturn(doctorAvailability);
		Mockito.when(slotRepository.saveAll(slots)).thenReturn(slots);
		
		List<Slot> doctorSlotResponseDtos= slotServiceImpl.addSlots(doctorId, addSlotRequestDto);
		assertEquals(0, doctorSlotResponseDtos.size());

	}
	
	@Test
	public void testAddSlotsByDoctorIdForNegative() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorAvailabilityRepository.save(null)).thenReturn(Optional.ofNullable(null));
		Mockito.when(slotRepository.saveAll(slots)).thenReturn(slots);
		
		List<Slot> doctorSlotResponseDtos= slotServiceImpl.addSlots(doctorId, addSlotRequestDto);
		assertEquals(0, doctorSlotResponseDtos.size());

	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testAddSlotsByDoctorIdException() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.ofNullable(null));
		List<Slot> doctorSlotResponseDtos= slotServiceImpl.addSlots(doctorId, addSlotRequestDto);
		assertNull(doctorSlotResponseDtos);
	}
	
	@Test
	public void testGetAvailableSlotsPositive() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorAvailabilityRepository.findByDoctor(doctor)).thenReturn(doctorAvailabilities);
		Mockito.when(slotRepository.getSlotsByDoctorAvailability(doctorAvailability)).thenReturn(slots);
		Mockito.when(bookingRepository.findBySlot(slot)).thenReturn(Optional.of(booking));

		List<AvailableSlotDto> doctorSlotResponseDtos= slotServiceImpl.getAvailableSlots(doctorId);
		assertEquals(1, doctorSlotResponseDtos.size());
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetAvailableSlotsNegative() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.ofNullable(null));

		List<AvailableSlotDto> doctorSlotResponseDtos= slotServiceImpl.getAvailableSlots(doctorId);
		assertNull(doctorSlotResponseDtos);
	}
	
	@Test(expected = DoctorNotFoundException.class)
	public void testGetAvailableSlotsException() throws DoctorNotFoundException{
		Long doctorId=1L;
		Mockito.when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
		Mockito.when(doctorAvailabilityRepository.findByDoctor(doctor)).thenReturn(Collections.emptyList());

		List<AvailableSlotDto> doctorSlotResponseDtos= slotServiceImpl.getAvailableSlots(doctorId);
		assertNull(doctorSlotResponseDtos);
	}
}
