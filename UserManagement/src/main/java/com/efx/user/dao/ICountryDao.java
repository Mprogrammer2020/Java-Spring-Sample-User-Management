package com.efx.user.dao;

import java.util.List;

import com.efx.user.models.Country;


public interface ICountryDao {
	
	void persist(Country country);
	
	Country findById(Integer id);
	
	List<Country> findAll();
}
