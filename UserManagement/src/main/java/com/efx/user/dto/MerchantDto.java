package com.efx.user.dto;

import java.util.UUID;

public class MerchantDto {
	private UUID id;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String countryCode;
	private String image;
	private String address;
	private Double latitude;
	private Double longitude;
	
	public MerchantDto() {}
	
	public MerchantDto(UUID id,String email,String firstName, 
			String lastName,String phoneNumber, String countryCode,String image,
			String address,Double latitude, Double longitude) {
		this.id=id;
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber=phoneNumber;
		this.countryCode=countryCode;
		this.image=image;
		this.address=address;
		this.latitude=latitude;
		this.longitude = longitude;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getImage() {
		return image;
	}	
	

	public void setImage(String image) {
		this.image = image;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
