package com.efx.user.dao;

import com.efx.user.models.SocialType;

public interface ISocialTypeDao {

	void save(SocialType socialType);
	
	SocialType merge(SocialType socialType);
	
	SocialType findById(Integer id);
	
	SocialType findByType(String type);
	
}
