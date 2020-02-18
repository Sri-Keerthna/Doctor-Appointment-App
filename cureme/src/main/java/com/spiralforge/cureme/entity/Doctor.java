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
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;
	private String doctorName;
	private String qualification;
	private Float experience;
	private String specialization;
	private String diseaseCure;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}
