package com.spiralforge.cureme.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class SlotBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer slotBookingId;
	private LocalTime slotBookingTime;
	private String disease;
}
