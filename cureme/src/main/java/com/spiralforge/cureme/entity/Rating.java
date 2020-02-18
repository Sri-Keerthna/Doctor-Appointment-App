package com.spiralforge.cureme.entity;

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
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ratingId;
	private float ratingValue;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
}
