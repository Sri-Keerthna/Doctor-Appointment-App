package com.spiralforge.cureme.service;

import com.spiralforge.cureme.dto.OtpRequestDto;
import com.spiralforge.cureme.dto.OtpResponseDto;
import com.spiralforge.cureme.exception.InvalidOtpException;

public interface OtpService {

	OtpResponseDto getUserDetails(OtpRequestDto otpRequstDto) throws InvalidOtpException;

}
