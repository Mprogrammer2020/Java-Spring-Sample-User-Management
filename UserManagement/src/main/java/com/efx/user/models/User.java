package com.efx.user.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

@Entity
@Table(name="app_user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String countryCode;
	private String image;
	private String address;
	private Double latitude;
	private Double longitude;
	private String otp;
	
	private String businessName;
	
	@Type(type = "text")
	private String aboutYou;
	
	private Date lastPasswordResetDate;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "country")
	private Country country;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "role")
	private Role role;
	
	@OneToMany(mappedBy = "appUser", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	Set<SocialAccount> socialAccounts = new HashSet<>();
	
	private Date modifiedAt;

	@CreationTimestamp
	private Date createdAt;
	
	private Boolean status;
	private Boolean emailVerified;
	private Boolean mobileVerified;
	
	public User() {}
	
	public User(String email, Boolean emailVerified,Boolean mobileVerified, Date lastPasswordResetDate,Boolean status,Date createdAt, Date modifiedAt, Role role) {
		this.email=email;
		this.emailVerified=emailVerified;
		this.mobileVerified = mobileVerified;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.status =status;
		this.createdAt =createdAt;
		this.modifiedAt = modifiedAt;
		this.role =role;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Set<SocialAccount> getSocialAccounts() {
		return socialAccounts;
	}
	public void setSocialAccounts(Set<SocialAccount> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Boolean getEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Boolean getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(Boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAboutYou() {
		return aboutYou;
	}

	public void setAboutYou(String aboutYou) {
		this.aboutYou = aboutYou;
	}
	
}
