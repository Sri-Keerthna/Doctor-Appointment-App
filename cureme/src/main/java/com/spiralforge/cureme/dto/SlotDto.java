package com.spiralforge.cureme.dto;

import java.io.Serializable;
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
public class SlotDto implements Serializable {

	private Long slotId;

	
	@JsonFormat(pattern = "KK:mm a")
	private LocalTime slotTime;
	private String availableStatus;
}
