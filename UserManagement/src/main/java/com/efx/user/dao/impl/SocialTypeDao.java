package com.efx.user.dao.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.user.dao.ISocialTypeDao;
import com.efx.user.models.SocialType;

@Repository
@Transactional
public class SocialTypeDao implements ISocialTypeDao {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void save(SocialType socialType) {
		entityManager.persist(socialType);
	}

	@Override
	public SocialType merge(SocialType socialType) {
		return entityManager.merge(socialType);
	}

	@Override
	public SocialType findById(Integer id) {
		return entityManager.find(SocialType.class, id);
	}

	@Override
	public SocialType findByType(String type) {
		try {
			return entityManager.createQuery("from SocialType where type like '"+type+"'", SocialType.class).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
