package com.spiralforge.cureme.controller;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spiralforge.cureme.dto.OtpRequestDto;
import com.spiralforge.cureme.dto.OtpResponseDto;
import com.spiralforge.cureme.exception.InvalidOtpException;
import com.spiralforge.cureme.service.OtpService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OtpControllerTest {
	OtpRequestDto otpRequestDto = null;
	OtpResponseDto otpResponseDto = null;

	@InjectMocks
	OtpController otpController;

	@Mock
	OtpService otpService;

	@Before
	public void before() {
		otpRequestDto = new OtpRequestDto();
		otpResponseDto=new OtpResponseDto();
		otpRequestDto.setMobileNumber(9876543210L);
		otpRequestDto.setOtp(12345);
	}

	@Test
	public void testGetUserDetails() throws InvalidOtpException {
		Mockito.when(otpService.getUserDetails(otpRequestDto)).thenReturn(otpResponseDto);
		ResponseEntity<OtpResponseDto> response = otpController.getUserDetails(otpRequestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
