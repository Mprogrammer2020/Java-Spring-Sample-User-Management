package com.efx.user.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efx.user.dao.IRoleDao;
import com.efx.user.dao.IUserDao;
import com.efx.user.dto.BussinessInfoDto;
import com.efx.user.dto.CreateProfileDto;
import com.efx.user.dto.MerchantDto;
import com.efx.user.models.User;
import com.efx.user.services.IUserService;

@Service
public class UserServcie implements IUserService {

	@Autowired
	IUserDao userDao;
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
	@Autowired
	IRoleDao roleDao;
	
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public User merge(User user) {
		return userDao.merge(user);
	}

	@Override
	public User mergeAsNew(User user) {
		user.setPassword(user.getPassword());
		user.setRole(roleDao.findById(3));
		user.setCreatedAt(new Date());
		user.setLastPasswordResetDate(new Date());
		user.setModifiedAt(new Date());
		user.setStatus(true);
		user.setEmailVerified(false);
		return userDao.merge(user);
	}
	
	@Override
	public User registerMerchant(User user) {
		user.setPassword(user.getPassword());
		user.setRole(roleDao.findById(2));
		user.setCreatedAt(new Date());
		user.setLastPasswordResetDate(new Date());
		user.setModifiedAt(new Date());
		user.setStatus(true);
		user.setEmailVerified(false);
		return userDao.merge(user);
	}
	
	@Override
	public User updateBusinessInfo(BussinessInfoDto bussinessInfoDto,UUID id) {
		User user = userDao.findById(id);
		return userDao.merge(user);
	}
	
	@Override
	public User findById(UUID id) {
		return userDao.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public List<MerchantDto> findByCounryId(Integer id) {
		return userDao.findByCounryId(id);
	}

	@Override
	public User updateProfile(CreateProfileDto createProfileDto, User user) {
		 user.setFirstName(createProfileDto.getFirstName());
		 user.setLastName(createProfileDto.getLastName());
		 user.setPhoneNumber(createProfileDto.getPhoneNumber());
		 user.setCountryCode(createProfileDto.getCountryCode());
		 return userDao.merge(user);
	}

}
