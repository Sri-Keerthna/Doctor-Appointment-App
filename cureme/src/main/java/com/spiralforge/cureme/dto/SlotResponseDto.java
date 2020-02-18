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
public class SlotResponseDto implements Serializable {

	private List<DoctorSlotResponseDto> slots;
	private Integer statusCode;
	private String message;

}
