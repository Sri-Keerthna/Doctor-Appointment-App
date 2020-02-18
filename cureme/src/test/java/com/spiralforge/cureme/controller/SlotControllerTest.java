package com.spiralforge.cureme.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.cureme.constants.ApiConstant;
import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.dto.AddSlotResponseDto;
import com.spiralforge.cureme.dto.AvailableSlotDto;
import com.spiralforge.cureme.dto.AvailableSlotResponseDto;
import com.spiralforge.cureme.dto.DoctorSlotResponseDto;
import com.spiralforge.cureme.dto.SlotDto;
import com.spiralforge.cureme.dto.SlotResponseDto;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.DoctorAvailability;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.DoctorNotFoundException;
import com.spiralforge.cureme.exception.ValidationFailedException;
import com.spiralforge.cureme.service.SlotService;
import com.spiralforge.cureme.util.SlotValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SlotControllerTest {

	@InjectMocks
	private SlotController slotController;

	@Mock
	private SlotService slotService;
	
	@Mock
	private SlotValidator<AddSlotRequestDto> slotValidator;
	
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
		
	}

	@Test
	public void testGetSlotsByDoctorIdPositive() {
		Long doctorId=1L;
		Mockito.when(slotService.getSlotsByDoctorId(doctorId)).thenReturn(doctorSlotResponseDtos);
		SlotResponseDto response = slotController.getSlotsByDoctorId(doctorId).getBody();
		assertEquals(ApiConstant.SUCCESS_CODE, response.getStatusCode());
	}
	
	@Test
	public void testGetSlotsByDoctorIdNegative() {
		Long doctorId=1L;
		Mockito.when(slotService.getSlotsByDoctorId(doctorId)).thenReturn(Collections.emptyList());
		SlotResponseDto response = slotController.getSlotsByDoctorId(doctorId).getBody();
		assertEquals(ApiConstant.FAILURE_CODE, response.getStatusCode());
	}
	
	@Test
	public void testAddSlotsByDoctorIdPositive() throws DoctorNotFoundException, ValidationFailedException {
		Long doctorId=1L;
		Mockito.when(slotValidator.validate(addSlotRequestDto)).thenReturn(true);
		Mockito.when(slotService.addSlots(doctorId, addSlotRequestDto)).thenReturn(slots);
		AddSlotResponseDto response = slotController.addSlotsForDoctorId(doctorId, addSlotRequestDto).getBody();
		assertEquals(ApiConstant.SUCCESS_CODE, response.getStatusCode());
	}
	
	@Test
	public void testAddSlotsByDoctorIdNegative() throws ValidationFailedException, DoctorNotFoundException {
		Long doctorId=1L;
		Mockito.when(slotValidator.validate(addSlotRequestDto)).thenReturn(false);
		ResponseEntity<AddSlotResponseDto> response = slotController.addSlotsForDoctorId(doctorId, addSlotRequestDto);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void testAddSlotsByDoctorIdForNegative() throws DoctorNotFoundException, ValidationFailedException {
		Long doctorId=1L;
		Mockito.when(slotValidator.validate(addSlotRequestDto)).thenReturn(true);
		Mockito.when(slotService.addSlots(doctorId, addSlotRequestDto)).thenReturn(Collections.emptyList());
		AddSlotResponseDto response = slotController.addSlotsForDoctorId(doctorId, addSlotRequestDto).getBody();
		assertEquals(ApiConstant.FAILURE_CODE, response.getStatusCode());
	}
	
	@Test
	public void testGetAvailableSlotsPositive() throws DoctorNotFoundException, ValidationFailedException {
		Long doctorId=1L;
		Mockito.when(slotService.getAvailableSlots(doctorId)).thenReturn(availableDtos);
		AvailableSlotResponseDto response = slotController.getAvailableSlots(doctorId).getBody();
		assertEquals(ApiConstant.SUCCESS_CODE, response.getStatusCode());
	}
	
	@Test
	public void testGetAvailableSlotsNegative() throws ValidationFailedException, DoctorNotFoundException {
		Long doctorId=1L;
		Mockito.when(slotService.getAvailableSlots(doctorId)).thenReturn(Collections.emptyList());
		AvailableSlotResponseDto response = slotController.getAvailableSlots(doctorId).getBody();
		assertEquals(ApiConstant.FAILURE_CODE, response.getStatusCode());
	}

}
