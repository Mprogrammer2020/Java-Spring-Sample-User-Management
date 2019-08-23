package com.efx.user.dao.impl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efx.user.dao.IRoleDao;
import com.efx.user.models.Role;

@Repository
@Transactional
public class RoleDao implements IRoleDao {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void save(Role role) {
		 entityManager.persist(role);
	}

	@Override
	public Role findById(Integer id) {
		return entityManager.find(Role.class, id);
	}
	
}
