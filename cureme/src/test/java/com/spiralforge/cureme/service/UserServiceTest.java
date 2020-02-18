package com.spiralforge.cureme.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.LoginRequestDto;
import com.spiralforge.cureme.entity.Otp;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.UserNotFoundException;
import com.spiralforge.cureme.repository.UserRepository;
import com.spiralforge.cureme.util.CureMeEnum.Role;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
	User user = null;
	Otp otp = null;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	LoginRequestDto loginRequestDto = new LoginRequestDto();

	@Before
	public void before() {
		loginRequestDto = new LoginRequestDto();
		user = new User();
		otp = new Otp();
		user.setUserId(1L);
		user.setMobileNumber(9876543210L);
		user.setRole(Role.PATIENT);
		user.setEmailId("abc@gmail.com");
		otp.setOtpNumber(Mockito.anyInt());
		otp.setOtpStatus(ApplicationConstants.OTP_ACTIVE_MESSAGE);
		otp.setMobileNumber(9876543210L);

		loginRequestDto.setMobileNumber(9876543210L);
	}

	@Test(expected = UserNotFoundException.class)
	public void testCheckUserException() throws UserNotFoundException {
		Mockito.when(userRepository.findByMobileNumber(12345L)).thenReturn(user);
		userServiceImpl.checkUser(loginRequestDto);
	}
}
