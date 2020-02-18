package com.spiralforge.cureme.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.OtpRequestDto;
import com.spiralforge.cureme.dto.OtpResponseDto;
import com.spiralforge.cureme.entity.Doctor;
import com.spiralforge.cureme.entity.Otp;
import com.spiralforge.cureme.entity.User;
import com.spiralforge.cureme.exception.InvalidOtpException;
import com.spiralforge.cureme.repository.DoctorRepository;
import com.spiralforge.cureme.repository.OtpRepository;
import com.spiralforge.cureme.repository.UserRepository;
import com.spiralforge.cureme.util.CureMeEnum.Role;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OtpServiceTest {
	OtpRequestDto otpRequestDto = null;
	Doctor doctor = null;
	User user = null;
	Otp otp = null;

	OtpRequestDto otpRequestDto1 = null;
	User user1 = null;
	Otp otp1 = null;

	@InjectMocks
	private OtpServiceImpl otpServiceImpl;

	@Mock
	private OtpRepository otpRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private DoctorRepository doctorRepository;

	@Before
	public void before() {
		otpRequestDto = new OtpRequestDto();
		otpRequestDto.setMobileNumber(9876543210L);
		otpRequestDto.setOtp(12345);
		doctor = new Doctor();
		user = new User();
		otp = new Otp();
		otp.setOtpNumber(12345);;
		otp.setMobileNumber(9876543210L);
		otp.setOtpStatus(ApplicationConstants.OTP_ACTIVE_MESSAGE);
		user.setMobileNumber(9876543210L);
		user.setUserId(1L);
		user.setRole(Role.DOCTOR);
		doctor.setUser(user);
		doctor.setDoctorName("Muthu");
		doctor.setDoctorId(1L);

		otpRequestDto1 = new OtpRequestDto();
		user1 = new User();
		otpRequestDto1.setMobileNumber(9876543210L);
		otpRequestDto1.setOtp(12345);
		otp1 = new Otp();
		otp1.setOtpNumber(12345);
		otp1.setMobileNumber(9876543211L);
		otp1.setOtpStatus(ApplicationConstants.OTP_ACTIVE_MESSAGE);
		user1.setMobileNumber(9876543211L);
		user1.setUserId(1L);
		user1.setRole(Role.PATIENT);
	}

	@Test
	public void testGetUserDetailsDoctor() throws InvalidOtpException {
		Long mobileNumber = otpRequestDto.getMobileNumber();
		Integer otpValue = otpRequestDto.getOtp();
		Mockito.when(otpRepository.findByMobileNumberAndOtpNumberAndOtpStatus(mobileNumber, otpValue,
				ApplicationConstants.OTP_ACTIVE_MESSAGE)).thenReturn(Optional.of(otp));
		Mockito.when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(user);
		Mockito.when(doctorRepository.findByUser(user)).thenReturn(doctor);
		OtpResponseDto response = otpServiceImpl.getUserDetails(otpRequestDto);
		assertEquals(doctor.getDoctorId(), response.getDoctorId());
	}

	@Test(expected = InvalidOtpException.class)
	public void testGetUserDetailsOtpException() throws InvalidOtpException {
		Long mobileNumber = otpRequestDto.getMobileNumber();
		Integer otpValue = otpRequestDto.getOtp();
		Mockito.when(otpRepository.findByMobileNumberAndOtpNumberAndOtpStatus(mobileNumber, otpValue,
				ApplicationConstants.OTP_INACTIVE_MESSAGE)).thenReturn(Optional.ofNullable(null));
		otpServiceImpl.getUserDetails(otpRequestDto);
	}

	@Test
	public void testGetUserDetailsPatient() throws InvalidOtpException {
		Long mobileNumber = otpRequestDto1.getMobileNumber();
		Integer otpValue = otpRequestDto1.getOtp();
		Mockito.when(otpRepository.findByMobileNumberAndOtpNumberAndOtpStatus(mobileNumber, otpValue,
				ApplicationConstants.OTP_ACTIVE_MESSAGE)).thenReturn(Optional.of(otp1));
		Mockito.when(userRepository.findByMobileNumber(mobileNumber)).thenReturn(user1);
		OtpResponseDto response = otpServiceImpl.getUserDetails(otpRequestDto1);
		assertEquals(user1.getUserId(), response.getUserId());
	}
}
