package com.spiralforge.cureme.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujal.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class AvailableSlotResponseDto implements Serializable {
	
	private List<AvailableSlotDto> availableDates;

	private Integer statusCode;
	private String message;
}
