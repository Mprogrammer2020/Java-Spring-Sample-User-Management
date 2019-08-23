package com.efx.user.dto;

import javax.validation.constraints.NotNull;

public class VerifyOtpDto {

	@NotNull(message = "Missing otp.")
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
