package com.efx.gateway.dao;

import com.efx.gateway.models.SocialAccount;
import com.efx.gateway.models.User;

public interface IUserDao {

	User findByEmail(String email);
	
	SocialAccount findBySocialId(String socialId);
	
}
