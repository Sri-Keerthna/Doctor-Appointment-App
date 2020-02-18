package com.spiralforge.cureme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequestDto {
	private Long mobileNumber;
	private Integer otp;
}
