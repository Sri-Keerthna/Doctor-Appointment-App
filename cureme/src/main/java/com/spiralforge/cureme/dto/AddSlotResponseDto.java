package com.spiralforge.cureme.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sujal.
 * @since 2020-02-12.
 */
@Getter
@Setter
public class AddSlotResponseDto implements Serializable {

	private Integer statusCode;
	private String message;


}
