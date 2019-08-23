package com.efx.user.services;

import com.efx.user.dto.SocialLoginDto;
import com.efx.user.models.SocialAccount;

public interface ISocialAccountService {

	SocialAccount save(SocialAccount socialAccount);
	
	SocialAccount saveAsNew(SocialLoginDto socialLoginDto);

	SocialAccount findBySocialId(String socialId);
	
}
