package com.efx.user.dao;

import com.efx.user.models.Role;

public interface IRoleDao {

	void save(Role role);
	
	Role findById(Integer id);
	
}
