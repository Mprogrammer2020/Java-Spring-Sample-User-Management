package com.efx.user.dao.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.user.dao.IUserDao;
import com.efx.user.dto.MerchantDto;
import com.efx.user.models.User;

@Repository
@Transactional
public class UserDao implements IUserDao{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void save(User user) {
		entityManager.persist(user);
	}

	@Override
	public User merge(User user) {
		return entityManager.merge(user);
	}

	@Override
	public User findById(UUID id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		try {
			return entityManager.createQuery("from User where email like '"+email+"'", User.class).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<MerchantDto> findByCounryId(Integer id) {
		return entityManager.createQuery("select com.efx.user.dto.MerchantDto(u.id, u.email, u.firstName,"
				+ " u.lastName, u.phoneNumber, u.countryCode,u.image, u.address,u.latitude, u.longitude) from User u where u.role.id=2 and u.country.id=:id", MerchantDto.class)
				.setParameter("id", id).getResultList();
	}

}
