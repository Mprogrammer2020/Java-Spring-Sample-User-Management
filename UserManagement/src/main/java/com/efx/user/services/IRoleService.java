package com.efx.user.services;

import com.efx.user.models.Role;

public interface IRoleService {

	void save(Role role);
	
	Role findById(Integer id);
	
}
