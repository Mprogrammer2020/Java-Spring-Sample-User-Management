package com.efx.user.dao.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.user.dao.ISocialAccountDao;
import com.efx.user.models.SocialAccount;

@Repository
@Transactional
public class SocialAccountDao implements ISocialAccountDao {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public SocialAccount save(SocialAccount socialAccount) {
		return entityManager.merge(socialAccount);
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
