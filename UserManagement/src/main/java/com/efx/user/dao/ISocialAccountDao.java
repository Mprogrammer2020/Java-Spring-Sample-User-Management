package com.efx.user.dao;

import com.efx.user.models.SocialAccount;

public interface ISocialAccountDao {
	
	SocialAccount save(SocialAccount socialAccount);
	
	SocialAccount findBySocialId(String socialId);
	
}
