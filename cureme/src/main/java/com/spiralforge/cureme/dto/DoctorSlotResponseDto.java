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
public class DoctorSlotResponseDto implements Serializable {

	private Long bookingId;
	private Long slotId;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate slotDate;
	
	@JsonFormat(pattern = "KK:mm a")
	private LocalTime slotTime;
	private String disease;
	private Long mobileNumber;
	private String emailId;
}
