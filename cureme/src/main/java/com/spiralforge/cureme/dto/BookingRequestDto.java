package com.spiralforge.cureme.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class BookingRequestDto {

	private Long doctorId;
	private Long slotId;
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate slotDate;
	
	@JsonFormat(pattern = "KK:mm a")
	private LocalTime slotTime;
	
	private Long mobileNumber;
	@Email(message = "Email should be valid")
	private String emailId;
	@Size(min = 5, max = 200)
	private String disease;
}
