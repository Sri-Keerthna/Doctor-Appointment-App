package com.spiralforge.cureme.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujal.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class AvailableSlotDto implements Serializable {

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate slotDate;
	
	private List<SlotDto> slots;

}
