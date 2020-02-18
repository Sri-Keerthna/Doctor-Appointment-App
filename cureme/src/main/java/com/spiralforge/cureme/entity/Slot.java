package com.spiralforge.cureme.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Slot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long slotId;
	private LocalTime slotTime;
	
	@ManyToOne
	@JoinColumn(name="doctor_availability_id")
	private DoctorAvailability doctorAvailability;

}
