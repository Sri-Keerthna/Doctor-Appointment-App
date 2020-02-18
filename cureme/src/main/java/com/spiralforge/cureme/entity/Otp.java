package com.spiralforge.cureme.entity;

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
public class Otp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer otpId;
	private Long mobileNumber;
	private String otpStatus;
	private Integer otpNumber;
}
