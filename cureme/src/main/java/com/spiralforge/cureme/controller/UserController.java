package com.spiralforge.cureme.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spiralforge.cureme.dto.BookingRequestDto;
import com.spiralforge.cureme.dto.BookingResponseDto;
import com.spiralforge.cureme.dto.DoctorResponseDto;
import com.spiralforge.cureme.dto.LoginRequestDto;
import com.spiralforge.cureme.dto.ResponseDto;
import com.spiralforge.cureme.exception.DoctorsNotFoundException;
import com.spiralforge.cureme.exception.LocationNotFoundException;
import com.spiralforge.cureme.exception.SlotNotFoundException;
import com.spiralforge.cureme.exception.UserNotFoundException;
import com.spiralforge.cureme.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sri Keerthna.
 * @since 2020-02-12.
 */
@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-11. In this method user will search doctor by giving location
	 *        and other filterations using search key.
	 * @param location  given by user.
	 * @param searchkey it can be doctor's specialization, qualification,
	 *                  diseaseCure and doctor name.
	 * @return list of doctors with rating.
	 * @throws LocationNotFoundException if in that location no doctors available.
	 * @throws DoctorsNotFoundException  if doctors have no availability.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<DoctorResponseDto>> getDoctorsList(@RequestParam String location,
			@RequestParam String searchkey) throws LocationNotFoundException, DoctorsNotFoundException {
		logger.info("Entered into getDoctorsList method in user controller");
		List<DoctorResponseDto> responseList = userService.getDoctorsList(location, searchkey);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-02-11. In this method user can book slot for a particular doctor
	 *        by giving some details.
	 * @param bookingRequestDto has slotId, slotTime, slotDate with doctorId.
	 * @return message will be sent if it is booked successfully.
	 * @throws DoctorsNotFoundException if the doctor is not available.
	 * @throws SlotNotFoundException    if slot is not available for that doctor.
	 * 
	 */
	@PostMapping("/bookslot")
	public ResponseEntity<BookingResponseDto> bookSlot(@Valid @RequestBody BookingRequestDto bookingRequestDto)
			throws DoctorsNotFoundException, SlotNotFoundException {
		logger.info("Entered into bookSlot method in user controller");
		BookingResponseDto bookingResponseDto = userService.bookSlot(bookingRequestDto);
		return new ResponseEntity<>(bookingResponseDto, HttpStatus.OK);
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used to check the mobile number is present or not
	 * 
	 * @param loginRequestDto which takes the input as mobile number
	 * @return ResponseDto return a success message
	 * @throws UserNotFoundException thrown when the mobile number is not found
	 */
	@PostMapping
	public ResponseEntity<ResponseDto> checkUser(@RequestBody LoginRequestDto loginRequestDto)
			throws UserNotFoundException {
		log.info("For checking the entered mobile number is valid or not");
		String response = userService.checkUser(loginRequestDto);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(response);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
