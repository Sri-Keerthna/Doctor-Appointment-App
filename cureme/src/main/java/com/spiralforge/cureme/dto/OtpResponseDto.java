package com.spiralforge.cureme.dto;

import com.spiralforge.cureme.util.CureMeEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpResponseDto {
	private Long userId;
	private String emailId;
	private String messsge;
	private Long doctorId;
	private Role role;
}
