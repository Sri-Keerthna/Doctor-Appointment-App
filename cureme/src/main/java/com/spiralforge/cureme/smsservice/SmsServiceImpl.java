package com.spiralforge.cureme.smsservice;

import org.springframework.stereotype.Service;

import com.spiralforge.cureme.constants.ApplicationConstants;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
	public static final String ACCOUNT_SID = "abc";
	public static final String AUTH_TOKEN = "vcxb";
	public static final String TWILIO_NUMBER = "azb";


	@Override
	public Message sendSMS(Long mobileNumber, Integer otp) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String userMobileNumber = "+91" + mobileNumber;
		Message message = Message
				.creator(new com.twilio.type.PhoneNumber(userMobileNumber),
						new com.twilio.type.PhoneNumber(TWILIO_NUMBER), ApplicationConstants.OTP_MESSAGE + otp)
				.create();
		log.info("Otp sent successfully");
		return message;
	}

}
