package com.efx.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efx.user.dao.ICountryDao;
import com.efx.user.models.Country;
import com.efx.user.services.ICountryService;

@Service
public class CountryService implements ICountryService {

	@Autowired
	ICountryDao countryDao;
	
	@Override
	public void persist(Country country) {
		countryDao.persist(country);
	}

	@Override
	public List<Country> findAll() {
		return countryDao.findAll();
	}

	@Override
	public Country findById(Integer id) {
		return countryDao.findById(id);
	}

}
