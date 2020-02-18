package com.spiralforge.cureme.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spiralforge.cureme.util.CureMeEnum.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String emailId;
	private Long mobileNumber;
	@Enumerated(EnumType.STRING)
	private Role role;

}
