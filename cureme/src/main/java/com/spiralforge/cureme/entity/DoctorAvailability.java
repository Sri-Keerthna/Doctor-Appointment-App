package com.spiralforge.cureme.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class DoctorAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorAvailabilityId;
	
	private LocalDate availableDate;
	private LocalTime availableFrom;
	private LocalTime availableTo;
	private String location;
	
	@OneToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	private String availableStatus;
	private LocalDateTime createdDate;
}
