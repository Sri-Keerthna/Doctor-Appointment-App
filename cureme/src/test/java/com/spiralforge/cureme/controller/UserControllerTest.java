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

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.LoginRequestDto;
import com.spiralforge.cureme.dto.ResponseDto;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.UserNotFoundException;
import com.spiralforge.cureme.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {
	LoginRequestDto loginRequestDto = null;
	User user = null;

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Before
	public void before() {
		loginRequestDto = new LoginRequestDto();
		user = new User();
		loginRequestDto.setMobileNumber(9876543210L);
		user.setUserId(1L);
		user.setMobileNumber(9876543210L);
	}

	@Test
	public void testCheckUser() throws UserNotFoundException {
		Mockito.when(userService.checkUser(loginRequestDto)).thenReturn(ApplicationConstants.OTP_SENT_MESSAGE);
		ResponseEntity<ResponseDto> response = userController.checkUser(loginRequestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
