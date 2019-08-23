package com.efx.user.services;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface IMultipartFileServices {

	String saveUserImage(MultipartFile file, UUID id);
	
}
