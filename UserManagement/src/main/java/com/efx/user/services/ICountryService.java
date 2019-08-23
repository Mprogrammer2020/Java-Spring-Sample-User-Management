package com.efx.user.services;

import java.util.List;

import com.efx.user.models.Country;

public interface ICountryService {

	void persist(Country country);

	Country findById(Integer id);
	
	List<Country> findAll();
	
}
