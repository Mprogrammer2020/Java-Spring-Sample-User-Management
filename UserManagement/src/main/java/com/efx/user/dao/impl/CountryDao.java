package com.efx.user.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.user.dao.ICountryDao;
import com.efx.user.models.Country;

@Repository
@Transactional
public class CountryDao implements ICountryDao {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void persist(Country country) {
		entityManager.persist(country);
	}

	@Override
	public List<Country> findAll() {
		return entityManager.createQuery("from Country",Country.class).getResultList();
	}

	@Override
	public Country findById(Integer id) {
		try {
			return entityManager.createQuery("from Country where id=:id",Country.class)
					.setParameter("id", id).getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
