package com.spiralforge.cureme.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spiralforge.cureme.constants.ApiConstant;
import com.spiralforge.cureme.constants.ApplicationConstants;
import com.spiralforge.cureme.dto.ErrorDto;
import com.spiralforge.cureme.dto.ExceptionResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * @description Handle NullPointer Exception
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public final ExceptionResponseDto handleNullPointerExceptions(NullPointerException exception) {
		String defaultMessage = exception.getMessage();
		return new ExceptionResponseDto(ApiConstant.NO_ELEMENT_FOUND, defaultMessage);
	}

	/**
	 * @description Handle MethodArgumentNotValidException
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ExceptionResponseDto handleValidationError(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		FieldError fieldError = bindingResult.getFieldError();
		String defaultMessage = fieldError.getDefaultMessage();
		return new ExceptionResponseDto(ApiConstant.VALIDATION_FAILED, defaultMessage);
	}

	/**
	 * @description Handle Runtime Exception
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public final ExceptionResponseDto handleAllRuntimeExceptions(RuntimeException exception) {
		String defaultMessage = exception.getMessage();
		return new ExceptionResponseDto(ApiConstant.INTERNAL_SERVER_ERROR, defaultMessage);
	}

	/**
	 * @description All Handle Exception
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public final ExceptionResponseDto handleAllExceptions(Exception exception) {
		String defaultMessage = exception.getMessage();
		return new ExceptionResponseDto(ApiConstant.INTERNAL_SERVER_ERROR, defaultMessage);
	}
	
	/**
	 * @description All Handle Exception
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ValidationFailedException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public final ExceptionResponseDto handleValidationFailedException(ValidationFailedException exception) {
		String defaultMessage = exception.getMessage();
		return new ExceptionResponseDto(ApiConstant.INTERNAL_SERVER_ERROR, defaultMessage);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> userNotFoundException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(ApplicationConstants.USER_NOTFOUND_MESSAGE);
		errorDto.setStatusCode(ApplicationConstants.NOTFOUND_CODE);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
	}
	
	@ExceptionHandler(InvalidOtpException.class)
	public ResponseEntity<ErrorDto> invalidOtpException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setMessage(ApplicationConstants.OTP_INVALID_MESSAGE);
		errorDto.setStatusCode(ApplicationConstants.NOTFOUND_CODE);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
	}

}
