package com.spiralforge.cureme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
	private String message;
	private Integer statusCode;
}
