package com.spiralforge.cureme.entity;

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
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	private String bookingStatus;
	private String disease;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "slot_id")
	private Slot slot;
	
}
