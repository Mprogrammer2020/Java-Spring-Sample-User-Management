package com.efx.user.models;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "social_account")
public class SocialAccount {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String socialId;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "appUser")
	private User appUser;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "socialType")
	private SocialType socialType;

	
	public SocialAccount() {}
	
	public SocialAccount(String socialId, User appUser, SocialType socialType) {
		this.socialId=socialId;
		this.appUser = appUser;
		this.socialType=socialType;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	@JsonIgnore
	public User getAppUser() {
		return appUser;
	}

	@JsonProperty
	public void setAppUser(User appUser) {
		this.appUser = appUser;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}

}


