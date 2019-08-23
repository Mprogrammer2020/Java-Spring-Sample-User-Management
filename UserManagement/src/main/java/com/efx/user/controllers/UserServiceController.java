package com.efx.user.controllers;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efx.user.dto.UserProfileDto;
import com.efx.user.models.User;
import com.efx.user.services.ITwillioService;
import com.efx.user.services.IUserService;

@RestController
@RequestMapping("/v1/api/")
public class UserServiceController {

	@Autowired
	IUserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ITwillioService twillioService;
	
	@RequestMapping(value="users/{id}", method=RequestMethod.GET)
	public UserProfileDto getUser(@PathVariable("id") UUID id) {
		return modelMapper.map(userService.findById(id), UserProfileDto.class);
	}
	
	@GetMapping(value="users/{id}/sendOtp")
	public UserProfileDto sendTransactionOtp(@PathVariable("id") UUID id, @RequestParam("otp") String otp) {
		User user =userService.findById(id);
		if(user.getPhoneNumber()!=null)
			twillioService.sendTransactionOtp(user.getCountryCode(), user.getPhoneNumber(), otp);
		return modelMapper.map(user, UserProfileDto.class);
	}
}
