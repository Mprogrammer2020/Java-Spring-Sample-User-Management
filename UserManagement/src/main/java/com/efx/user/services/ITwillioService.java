package com.efx.user.services;

import com.squareup.okhttp.Call;
import com.twilio.rest.api.v2010.account.Message;

public interface ITwillioService {

	Call sendOtpBySms(String phoneNo,String countryCode);
	
	Call sendOtpByCall(String phoneNo,String countryCode);
	
	Call verifyOtp(String phoneNo,String countryCode,String otp);
	
	Message sendCustomOtp(String countryCode,String phoneNo,String otp); 
	
	Message sendTransactionOtp(String countryCode,String phoneNo,String otp);
}
