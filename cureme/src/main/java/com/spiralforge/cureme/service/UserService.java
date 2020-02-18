package com.spiralforge.cureme.service;

import java.util.List;

import com.spiralforge.cureme.dto.BookingRequestDto;
import com.spiralforge.cureme.dto.BookingResponseDto;
import com.spiralforge.cureme.dto.DoctorResponseDto;
import com.spiralforge.cureme.dto.LoginRequestDto;
import com.spiralforge.cureme.exception.DoctorsNotFoundException;
import com.spiralforge.cureme.exception.LocationNotFoundException;
import com.spiralforge.cureme.exception.SlotNotFoundException;
import com.spiralforge.cureme.exception.UserNotFoundException;

public interface UserService {

	List<DoctorResponseDto> getDoctorsList(String location, String searchkey)
			throws LocationNotFoundException, DoctorsNotFoundException;

	BookingResponseDto bookSlot(BookingRequestDto bookingRequestDto)
			throws DoctorsNotFoundException, SlotNotFoundException;

	String checkUser(LoginRequestDto loginRequestDto) throws UserNotFoundException;

}
