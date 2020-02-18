package com.spiralforge.cureme.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.spiralforge.cureme.util.CureMeEnum;

import lombok.extern.slf4j.Slf4j;

/*
 * Method is used to verify the otp sent to the user mobile number
 */
@Service
@Slf4j
public class OtpServiceImpl implements OtpService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	/**
	 * @author Muthu
	 * 
	 *         Method is used to verify the otp sent to the user mobile number
	 * 
	 * @param otpRequstDto which takes the input as mobile number and otp
	 * @return OtpResponseDto which includes the id,role
	 * @throws InvalidOtpException thrown when the otp is already used or not found
	 */
	@Override
	public OtpResponseDto getUserDetails(OtpRequestDto otpRequestDto) throws InvalidOtpException {
		Long mobileNumber = otpRequestDto.getMobileNumber();
		Integer otp = otpRequestDto.getOtp();
		Optional<Otp> otpDetails = otpRepository.findByMobileNumberAndOtpNumberAndOtpStatus(mobileNumber, otp,
				ApplicationConstants.OTP_ACTIVE_MESSAGE);
		if (!(otpDetails.isPresent())) {
			log.error(ApplicationConstants.OTP_INVALID_MESSAGE);
			throw new InvalidOtpException(ApplicationConstants.OTP_INVALID_MESSAGE);
		}
		otpDetails.get().setOtpStatus(ApplicationConstants.OTP_INACTIVE_MESSAGE);
		otpRepository.save(otpDetails.get());
		User user = userRepository.findByMobileNumber(mobileNumber);
		OtpResponseDto otpResponseDto = new OtpResponseDto();
		if (user.getRole().equals(CureMeEnum.Role.DOCTOR)) {
			Doctor doctor = doctorRepository.findByUser(user);
			otpResponseDto.setDoctorId(doctor.getDoctorId());
			BeanUtils.copyProperties(user, otpResponseDto);
			return otpResponseDto;
		}
		BeanUtils.copyProperties(user, otpResponseDto);
		return otpResponseDto;
	}

}
