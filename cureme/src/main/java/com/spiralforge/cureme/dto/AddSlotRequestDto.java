package com.spiralforge.cureme.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujal.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class AddSlotRequestDto implements Serializable {

	private LocalDate availableFromDate;
	private LocalDate availableToDate;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime availableFrom;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime availableTo;
	private String location;


}
