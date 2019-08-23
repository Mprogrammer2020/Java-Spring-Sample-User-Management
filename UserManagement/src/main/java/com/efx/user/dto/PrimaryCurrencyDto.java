package com.efx.user.dto;

import java.util.UUID;


public class PrimaryCurrencyDto {

	private UUID id;
	private CurrencyDto currencyDto;
	private UUID userId;
	
	public PrimaryCurrencyDto() {}
	
	public PrimaryCurrencyDto(CurrencyDto currencyDto, UUID userId) {
		this.currencyDto=currencyDto;
		this.userId = userId;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public CurrencyDto getCurrency() {
		return currencyDto;
	}
	
	public void setCurrency(CurrencyDto currencyDto) {
		this.currencyDto = currencyDto;
	}
	
	public UUID getUserId() {
		return userId;
	}
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
}
