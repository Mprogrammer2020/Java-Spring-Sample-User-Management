package com.efx.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efx.user.dao.IRoleDao;
import com.efx.user.models.Role;
import com.efx.user.services.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	IRoleDao roleDao;
	
	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public Role findById(Integer id) {
		return roleDao.findById(id);
	}
	
}
