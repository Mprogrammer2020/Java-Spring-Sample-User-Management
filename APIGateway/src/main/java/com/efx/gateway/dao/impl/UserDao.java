package com.efx.gateway.dao.impl;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.gateway.dao.IUserDao;
import com.efx.gateway.models.SocialAccount;
import com.efx.gateway.models.User;


@Repository
@Transactional
public class UserDao implements IUserDao{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public User findByEmail(String email) {
		try {
			return entityManager.createQuery("from User where email like '"+email+"'", User.class).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public SocialAccount findBySocialId(String socialId) {
		try {
			return entityManager.createQuery("from SocialAccount where socialId = '"+socialId+"'",SocialAccount.class).getSingleResult();	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
