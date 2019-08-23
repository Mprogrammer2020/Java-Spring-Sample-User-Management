package com.efx.user.services.impl;

import org.springframework.stereotype.Service;

import com.efx.user.services.ITwillioService;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class TwillioServcie implements ITwillioService {

	//String apiKey="eVVThKr3ZcC4EZM59ZfJ29dIcBPU4zt9";
		String apiKey="c5M0pEPnL920CbjhqAuc8d9EwXJCLMS2";
		String apiEndPoint = "https://api.authy.com";
		
		
//		public final String ACCOUNT_SID =
//	            "AC5f5423afd7d4a4fb0748452ebdfce14e";
		
//		public final String AUTH_TOKEN =
//		            "9ad42720b1877448269c2b989bc9c00b";
		  
		public final String ACCOUNT_SID =
	            "ACb15043578288382fb938796288b39994";

		public final String AUTH_TOKEN =
		            "f493b5f7c6462ffe4f30ecee6a0aec71";
	  
		
		@Override
		public Call sendOtpBySms(String phoneNo,String countryCode) {
			OkHttpClient client = new OkHttpClient();
			// create new client
			Request request;
			FormEncodingBuilder builder = new FormEncodingBuilder();
			builder.add("phone_number", phoneNo);
			builder.add("country_code", countryCode);
			builder.add("via", "sms");
			builder.add("locale", "en");
			RequestBody body = builder.build();
			request = new Request.Builder()
					.url("https://api.authy.com/protected/json/phones/verification/start?api_key="+apiKey)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.post(body)
					.build();
			Call call = client.newCall(request);
			return call;
		}

		
		@Override
		public Call sendOtpByCall(String phoneNo,String countryCode) {
			OkHttpClient client = new OkHttpClient();
			// create new client
			Request request;
			FormEncodingBuilder builder = new FormEncodingBuilder();
			builder.add("phone_number", phoneNo);
			builder.add("country_code", countryCode);
			builder.add("via", "call");
			builder.add("locale", "en");
			RequestBody body = builder.build();
			request = new Request.Builder()
					.url("https://api.authy.com/protected/json/phones/verification/start?api_key="+apiKey)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.post(body)
					.build();
			Call call = client.newCall(request);
			return call;
		}
		
		@Override
		public Call verifyOtp(String phoneNo,String countryCode,String otp) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("https://api.authy.com/protected/json/phones/verification/check?"
							+ "phone_number="+phoneNo+"&country_code="+countryCode+"&verification_code="+otp)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.addHeader("X-Authy-API-Key", apiKey)
					.get().build();
			Call call = client.newCall(request);
			return call;
		}
		
		
		public Message sendCustomOtp(String countryCode, String phoneNo,String otp) {
			 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			 countryCode = countryCode.replace("+", "");
		     System.out.println("Phone no >>> "+phoneNo);
			 Message message = Message.creator(
		        		new com.twilio.type.PhoneNumber("+"+countryCode+phoneNo),
		                new com.twilio.type.PhoneNumber("+12522070631"),
		                "Your verification code for EFX is "+otp)
		            .create();
			return message;
		}
		
		public Message sendTransactionOtp(String countryCode, String phoneNo,String otp) {
			 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			 countryCode = countryCode.replace("+", "");
		     System.out.println("Phone no >>> "+phoneNo);
			 Message message = Message.creator(
		        		new com.twilio.type.PhoneNumber("+"+countryCode+phoneNo),
		                new com.twilio.type.PhoneNumber("+12522070631"),
		                "Your one time password for EFX transaction is "+otp)
		            .create();
			return message;
		}
}
