package com.efx.user.dto;

import javax.validation.constraints.NotNull;

public class CreateProfileDto {

	@NotNull(message = "Missing firstName.")
	private String firstName;
	@NotNull(message = "Missing lastName.")
	private String lastName;
	@NotNull(message = "Missing phoneNumber")
	private String phoneNumber;
	@NotNull(message = "Missing countryCode.")
	private String countryCode;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
