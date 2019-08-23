package com.efx.user.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.efx.user.models.Role;
import com.efx.user.models.SocialAccount;

public class UserDto {

	private UUID id;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String countryCode;
	private String image;
	private Boolean emailVerified;
	private Date createdAt;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "role")
	private Role role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	Set<SocialAccount> socialAccounts = new HashSet<>();
	
	public UserDto() {}
	
	public UserDto(UUID id, String email, String firstName, String lastName,
			String phoneNumber, String image, String countryCode, Boolean emailVerified, Date createdAt,Role role) {
		this.id = id;
		this.email=email;
		this.firstName =firstName;
		this.lastName = lastName;
		this.image = image;
		this.phoneNumber = phoneNumber;
		this.emailVerified=emailVerified;
		this.countryCode = countryCode;
		this.role=role;
		this.createdAt = createdAt;
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

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<SocialAccount> getSocialAccounts() {
		return socialAccounts;
	}

	public void setSocialAccounts(Set<SocialAccount> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
	
	
}
