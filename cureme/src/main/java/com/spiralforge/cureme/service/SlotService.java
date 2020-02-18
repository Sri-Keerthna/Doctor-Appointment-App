package com.spiralforge.cureme.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.dto.AvailableSlotDto;
import com.spiralforge.cureme.dto.DoctorSlotResponseDto;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.exception.DoctorNotFoundException;

/**
 * @author Sri Keerthna.
 * @since 2020-02-05.
 */
public interface SlotService {

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method list of booked slots for a doctor are fetched from
	 *        database.
	 * 
	 * @param doctorId
	 * @return List of booked slots are fetched.
	 * 
	 */
	List<DoctorSlotResponseDto> getSlotsByDoctorId(Long doctorId);

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method add the slots for a doctor in database
	 * 
	 * @param doctorId
	 * @return Slot created status.
	 * @throws DoctorNotFoundException 
	 */
	List<Slot> addSlots(@NotNull Long doctorId, @Valid AddSlotRequestDto addSlotRequestDto) throws DoctorNotFoundException;

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method fetch the available slots for a doctor in database
	 * 
	 * @param doctorId
	 * @return Slot created status.
	 * @throws DoctorNotFoundException 
	 */
	List<AvailableSlotDto> getAvailableSlots(@NotNull Long doctorId) throws DoctorNotFoundException;

}
