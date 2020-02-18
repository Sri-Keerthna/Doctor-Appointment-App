package com.spiralforge.cureme.smsservice;

import com.twilio.rest.api.v2010.account.Message;

public interface SmsService {

	Message sendSMS(Long mobileNumber,Integer otp);

}
