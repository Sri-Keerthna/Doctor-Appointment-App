package com.spiralforge.cureme.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.spiralforge.cureme.dto.BookingRequestDto;
import com.spiralforge.cureme.dto.BookingResponseDto;
import com.spiralforge.cureme.dto.DoctorResponseDto;
import com.spiralforge.cureme.exception.DoctorsNotFoundException;
import com.spiralforge.cureme.exception.LocationNotFoundException;
import com.spiralforge.cureme.exception.SlotNotFoundException;
import com.spiralforge.cureme.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestUserController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TestUserController.class);

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	
	List<DoctorResponseDto> responseList=new ArrayList<>();
	BookingResponseDto bookingResponseDto = new BookingResponseDto();	
	BookingRequestDto bookingRequestDto = new BookingRequestDto();
	
	@Test
	public void testGetDoctorsList() throws LocationNotFoundException, DoctorsNotFoundException {
		logger.info("Entered into getDoctorsList method in user controller");
		Mockito.when(userService.getDoctorsList("bangalore", "cough")).thenReturn(responseList);
		ResponseEntity<List<DoctorResponseDto>> response = userController.getDoctorsList("bangalore", "cough");
		assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	public void testBookSlotList() throws DoctorsNotFoundException, SlotNotFoundException  {
		logger.info("Entered into bookSlot method in user controller");
		Mockito.when(userService.bookSlot(bookingRequestDto)).thenReturn(bookingResponseDto);
		ResponseEntity<BookingResponseDto> response = userController.bookSlot(bookingRequestDto);
		assertEquals(200, response.getStatusCodeValue());
	}
}
