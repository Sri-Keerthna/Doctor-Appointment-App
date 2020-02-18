package com.spiralforge.cureme.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class DoctorResponseDto {

	private Long doctorId;
	private String doctorName;
	private String qualification;
	private Float experience;
	private String specialization;
	private String diseaseCure;
	private String location;
	private float rating;
}
