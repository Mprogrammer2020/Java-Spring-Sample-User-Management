package com.efx.user.services;

import java.util.List;
import java.util.UUID;

import com.efx.user.dto.BussinessInfoDto;
import com.efx.user.dto.CreateProfileDto;
import com.efx.user.dto.MerchantDto;
import com.efx.user.models.User;

public interface IUserService {

	void save(User user);
	
	User merge(User user);
	
	User findById(UUID id);
	
	User updateProfile(CreateProfileDto createProfileDto, User user);
	
	User updateBusinessInfo(BussinessInfoDto bussinessInfoDto, UUID id);
	
	User mergeAsNew(User user);
	
	User registerMerchant(User user);

	User findByEmail(String email);
	
	List<MerchantDto> findByCounryId(Integer id);
	
}
