package com.efx.user.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efx.user.dao.IRoleDao;
import com.efx.user.dao.ISocialAccountDao;
import com.efx.user.dao.ISocialTypeDao;
import com.efx.user.dao.IUserDao;
import com.efx.user.dto.SocialLoginDto;
import com.efx.user.models.SocialAccount;
import com.efx.user.models.User;
import com.efx.user.services.ISocialAccountService;

@Service
public class SocialAccountService implements ISocialAccountService {

	@Autowired
	ISocialAccountDao socialAccountDao;

	@Autowired
	IUserDao userDao;
	
	@Autowired
	ISocialTypeDao socialTypeDao;
	
	@Autowired
	IRoleDao roleDao;
	
	@Override
	public SocialAccount save(SocialAccount socialAccount) {
		return socialAccountDao.save(socialAccount);
	}

	@Override
	public SocialAccount findBySocialId(String socialId) {
		return socialAccountDao.findBySocialId(socialId);
	}

	@Override
	public SocialAccount saveAsNew(SocialLoginDto socialLoginDto) {
		if(socialLoginDto.getEmail() != null) {
			User user = userDao.findByEmail(socialLoginDto.getEmail());
			if(user != null) {
				return socialAccountDao.save(new SocialAccount(socialLoginDto.getSocialId(),user,socialTypeDao.findByType(socialLoginDto.getType())));
			} else {
				return socialAccountDao.save(new SocialAccount(socialLoginDto.getSocialId(),
						 userDao.merge(new User(socialLoginDto.getEmail(), true, false, new Date(), true, new Date(), new Date(),roleDao.findById(2))),
						 socialTypeDao.findByType(socialLoginDto.getType())));
			}
		} else {
			return socialAccountDao.save(new SocialAccount(socialLoginDto.getSocialId(),
					userDao.merge(new User(null, true, false, new Date(), true, new Date(), new Date(),roleDao.findById(2))),
					 socialTypeDao.findByType(socialLoginDto.getType())));
		}
	}

}
