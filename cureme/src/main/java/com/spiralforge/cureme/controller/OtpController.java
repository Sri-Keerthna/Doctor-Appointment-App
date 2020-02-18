package com.spiralforge.cureme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.OtpRequestDto;
import com.spiralforge.cureme.dto.OtpResponseDto;
import com.spiralforge.cureme.exception.InvalidOtpException;
import com.spiralforge.cureme.service.OtpService;

import lombok.extern.slf4j.Slf4j;

/*
 *  Method is used to verify the otp sent to the user mobile number
 * 
 */
@RestController
@Slf4j
@RequestMapping("/otp")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class OtpController {
	@Autowired
	OtpService otpService;

	/**
	 * @author Muthu
	 * 
	 *         Method is used to verify the otp sent to the user mobile number
	 * 
	 * @param otpRequstDto which takes the input as mobile number and otp
	 * @return OtpResponseDto which includes the id,role
	 * @throws InvalidOtpException thrown when the otp is already used or not found
	 */
	@PostMapping
	public ResponseEntity<OtpResponseDto> getUserDetails(@RequestBody OtpRequestDto otpRequstDto)
			throws InvalidOtpException {
		log.info("For checking whether the otp is valid or not");
		OtpResponseDto otpResponseDto = otpService.getUserDetails(otpRequstDto);
		otpResponseDto.setMesssge(ApplicationConstants.LOGIN_SUCCESS_MESSAGE);
		return new ResponseEntity<>(otpResponseDto, HttpStatus.OK);
	}

}
