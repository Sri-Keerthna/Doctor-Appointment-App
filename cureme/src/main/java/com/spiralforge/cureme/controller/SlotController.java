package com.spiralforge.cureme.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.cureme.constants.ApiConstant;
import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.dto.AddSlotResponseDto;
import com.spiralforge.cureme.dto.AvailableSlotDto;
import com.spiralforge.cureme.dto.AvailableSlotResponseDto;
import com.spiralforge.cureme.dto.DoctorSlotResponseDto;
import com.spiralforge.cureme.dto.SlotResponseDto;
import com.spiralforge.cureme.entity.Slot;
import com.spiralforge.cureme.exception.DoctorNotFoundException;
import com.spiralforge.cureme.exception.ValidationFailedException;
import com.spiralforge.cureme.service.SlotService;
import com.spiralforge.cureme.util.SlotValidator;

/**
 * This controller is having listing slot functionality.
 * 
 * @author Sujal
 * @since 2020-02-12.
 */
@RestController
@RequestMapping("/slots")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class SlotController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SlotController.class);

	@Autowired
	private SlotValidator<AddSlotRequestDto> slotValidator;

	@Autowired
	private SlotService slotService;

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method list of booked slots for a doctor.
	 * 
	 * @param doctorId
	 * @return List of booked slots are fetched.
	 * 
	 */
	@GetMapping("/doctors/{doctorId}")
	public ResponseEntity<SlotResponseDto> getSlotsByDoctorId(@NotNull @PathVariable("doctorId") Long doctorId) {
		logger.info("Entered into getSlotsByDoctorId method in controller");
		SlotResponseDto slotResponseDto = new SlotResponseDto();

		List<DoctorSlotResponseDto> slots = slotService.getSlotsByDoctorId(doctorId);
		if (slots.isEmpty()) {
			slotResponseDto.setStatusCode(ApiConstant.FAILURE_CODE);
			slotResponseDto.setMessage(ApiConstant.FAILED);
		} else {
			slotResponseDto.setSlots(slots);
			slotResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			slotResponseDto.setMessage(ApiConstant.SUCCESS);
		}
		return new ResponseEntity<>(slotResponseDto, HttpStatus.OK);
	}

	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method add the slots for a doctor
	 * 
	 * @param doctorId
	 * @return Slot created status.
	 * @throws ValidationFailedException
	 * @throws DoctorNotFoundException
	 */
	@PostMapping("/doctors/{doctorId}")
	public ResponseEntity<AddSlotResponseDto> addSlotsForDoctorId(@NotNull @PathVariable("doctorId") Long doctorId,
			@Valid @RequestBody AddSlotRequestDto addSlotRequestDto)
			throws ValidationFailedException, DoctorNotFoundException {
		logger.info("Entered into addSlotsForDoctorId method in controller");
		boolean validate = slotValidator.validate(addSlotRequestDto);
		if (Boolean.TRUE.equals(validate)) {
			AddSlotResponseDto addSlotResponseDto = new AddSlotResponseDto();

			List<Slot> slots = slotService.addSlots(doctorId, addSlotRequestDto);
			if (slots.isEmpty()) {
				addSlotResponseDto.setStatusCode(ApiConstant.FAILURE_CODE);
				addSlotResponseDto.setMessage(ApiConstant.FAILED);
			} else {
				addSlotResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
				addSlotResponseDto.setMessage(ApiConstant.SUCCESS);
			}
			return new ResponseEntity<>(addSlotResponseDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * @author Sujal.
	 * @since 2020-02-12. In this method list of available slots for a doctor.
	 * 
	 * @param doctorId
	 * @return List of booked slots are fetched.
	 * @throws DoctorNotFoundException 
	 * 
	 */
	@GetMapping("/doctors/{doctorId}/availableslots")
	public ResponseEntity<AvailableSlotResponseDto> getAvailableSlots(@NotNull @PathVariable("doctorId") Long doctorId) throws DoctorNotFoundException {
		logger.info("Entered into getSlotsByDoctorId method in controller");
		AvailableSlotResponseDto availableSlotResponseDto = new AvailableSlotResponseDto();

		List<AvailableSlotDto> availableDates = slotService.getAvailableSlots(doctorId);
		if (availableDates.isEmpty()) {
			availableSlotResponseDto.setStatusCode(ApiConstant.FAILURE_CODE);
			availableSlotResponseDto.setMessage(ApiConstant.FAILED);
		} else {
			availableSlotResponseDto.setAvailableDates(availableDates);
			availableSlotResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
			availableSlotResponseDto.setMessage(ApiConstant.SUCCESS);
		}
		return new ResponseEntity<>(availableSlotResponseDto, HttpStatus.OK);
	}
}
