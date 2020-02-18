package com.spiralforge.cureme.constants;

/**
 * 
 * @author Sujal
 *
 */
public class ApiConstant {
	
	private ApiConstant() {
	}

	public static final String LOGIN_ERROR = "please enter valid username and password";
	public static final String LOGIN_SUCCESS = "you are successfully logged in";
	public static final String CREDIT_CARD_TYPE = "credit";

	public static final String SUCCESS = "SUCCESSFUL";
	public static final String FAILED = "FAILED";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
	public static final String VALIDATION_FAILED = "VALIDATION FAILED";
	public static final String NO_ELEMENT_FOUND = "NO ELEMENT FOUND";
	public static final String CUSTOMER_NOT_FOUND = "Not a valid customer";

	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAILURE_CODE = 404;
	public static final Integer NO_CONTENT_CODE = 204;

	public static final String INVALID_SLOT_DETAIL = "Availablity data is not provided correcty.";
	public static final String INVALID_SLOT_FROMDATE = "Available from date should be after current date.";
	public static final String INVALID_SLOT_TODATE = "Available to date should be after current date.";

	public static final String INVALID_PAYMENT_TYPE = "Payment type is not provided.";

	public static final String DOCTOR_NOT_FOUND = "Doctor not found";
	public static final String DOCTOR_NOT_AVAILABLE = "Doctor slot not available";

	public static final String INVALID_SLOT_TIME = "Available from time should be less than to time";

}
