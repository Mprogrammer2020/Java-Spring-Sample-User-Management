package com.efx.user.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.efx.user.dto.BussinessInfoDto;
import com.efx.user.dto.MerchantDto;
import com.efx.user.dto.SignupDto;
import com.efx.user.dto.UserDto;
import com.efx.user.models.User;
import com.efx.user.security.JwtTokenUtil;
import com.efx.user.services.ICountryService;
import com.efx.user.services.IMultipartFileServices;
import com.efx.user.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/v1/api/")
public class MerchantController {

	@Autowired
	IUserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ICountryService countryService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IMultipartFileServices multipartFileServices;
	
	/*********************** Merchant Section  ******************************/
	
	@RequestMapping(value="merchants",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveMerchant(@RequestParam("data") String jsonStr, @RequestPart("file") MultipartFile file, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	 SignupDto signupDto = new ObjectMapper().readValue(jsonStr, SignupDto.class);
	    	 ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	         Validator validator = factory.getValidator();
	         Set<ConstraintViolation<SignupDto>> violations = validator.validate(signupDto);

	         if (!violations.isEmpty()) {
	        	Iterator<ConstraintViolation<SignupDto>> voi=violations.iterator();
	        	response.put("message", voi.next().getMessage());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
	         } else {
	        	 Map<String, Object> data = new HashMap<String, Object>();
	        	 User user = userService.findByEmail(signupDto.getEmail());
	        	 if(user != null) {
	        		 response.put("message", messageSource.getMessage("message.account.email_exist", null, Locale.forLanguageTag("en-US")));
	        		 return new ResponseEntity<>(response, HttpStatus.LOCKED);
	        	 }
	        	 user = userService.registerMerchant(modelMapper.map(signupDto, User.class));
	        	 user.setImage(multipartFileServices.saveUserImage(file, user.getId()));
	        	 UserDto userDto = modelMapper.map(userService.merge(user), UserDto.class);
	        	 
	        	 final String token = jwtTokenUtil.generateToken(modelMapper.map(userDto, User.class));
		    	
	        	 data.put("access_token", token);
	        	 data.put("user", userDto);
	        	 response.put("data", data);
	        	 response.put("message",messageSource.getMessage("message.account", null, Locale.forLanguageTag("en-US")));
	        	 return new ResponseEntity<>(response, HttpStatus.OK);
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			response.put("message", messageSource.getMessage("message.account.error", null, Locale.forLanguageTag("en-US")));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="merchants/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> saveMerchant(@PathVariable("id") UUID id, 
			@RequestBody @Valid BussinessInfoDto bussinessInfoDto,BindingResult result, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	if(bussinessInfoDto == null) {
				response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else if (result.hasFieldErrors()) {
				response.put("message", result.getFieldError().getDefaultMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else {
	        	 Map<String, Object> data = new HashMap<String, Object>();	        	 
	        	 UserDto userDto = modelMapper.map(userService.updateBusinessInfo(bussinessInfoDto, id), UserDto.class);

	        	 data.put("user", userDto);
	        	 response.put("data", data);
	        	 response.put("message",messageSource.getMessage("message.account", null, Locale.forLanguageTag("en-US")));
	        	 return new ResponseEntity<>(response, HttpStatus.OK);
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			response.put("message", messageSource.getMessage("message.account.error", null, Locale.forLanguageTag("en-US")));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="merchants",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMerchants(@RequestParam("countryId") Integer countryId, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	Map<String, Object> data = new HashMap<String, Object>();	        	 
	    	List<MerchantDto> merchantDtos= userService.findByCounryId(countryId);
	    	data.put("merchants", merchantDtos);
        	response.put("data", data);
        	response.put("message",messageSource.getMessage("message.account", null, Locale.forLanguageTag("en-US")));
        	return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
			e.printStackTrace();
			response.put("message", messageSource.getMessage("message.account.error", null, Locale.forLanguageTag("en-US")));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
