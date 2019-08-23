package com.efx.user.dao;

import java.util.List;
import java.util.UUID;

import com.efx.user.dto.MerchantDto;
import com.efx.user.models.User;

public interface IUserDao {
	
	void save(User user);
	
	User merge(User user);
	
	User findById(UUID id);

	User findByEmail(String email);
	
	List<MerchantDto> findByCounryId(Integer id);
	
}
