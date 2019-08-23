package com.efx.user.controllers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.efx.user.dto.ChangePasswordDto;
import com.efx.user.dto.CreateProfileDto;
import com.efx.user.dto.CurrencyDto;
import com.efx.user.dto.LoginDto;
import com.efx.user.dto.PrimaryCurrencyDto;
import com.efx.user.dto.SignupDto;
import com.efx.user.dto.SocialLoginDto;
import com.efx.user.dto.UpdatePasswordDto;
import com.efx.user.dto.UserDto;
import com.efx.user.dto.VerifyOtpDto;
import com.efx.user.models.SocialAccount;
import com.efx.user.models.User;
import com.efx.user.proxies.PrimaryCurrencyProxy;
import com.efx.user.security.JwtTokenUtil;
import com.efx.user.services.IEmailService;
import com.efx.user.services.ISocialAccountService;
import com.efx.user.services.ITwillioService;
import com.efx.user.services.IUserService;
import com.efx.user.services.Mail;
import com.twilio.rest.api.v2010.account.Message;

@Controller
@RequestMapping("/v1/api/")
public class UserController {

	@Autowired
	IUserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ISocialAccountService socialAccountService;
	
	@Autowired
	private ITwillioService twillioService;
	
	@Autowired
	private IEmailService emailService;
	
	@Autowired
	PrimaryCurrencyProxy primaryCurrencyProxy;
	
	/*********************** User Section  ******************************/
	
	@RequestMapping(value="users",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> save(@RequestBody @Valid SignupDto signupDto,BindingResult result, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	if(signupDto == null) {
				response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else if (result.hasFieldErrors()) {
				response.put("message", result.getFieldError().getDefaultMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else {
	    		Map<String, Object> data = new HashMap<String, Object>();
	    		User user = userService.findByEmail(signupDto.getEmail());
	    		if(user != null) {
	    			response.put("message", messageSource.getMessage("message.account.email_exist", null, Locale.forLanguageTag("en-US")));
					return new ResponseEntity<>(response, HttpStatus.LOCKED);
	    		}
	    		
	    		UserDto userDto = modelMapper.map(userService.mergeAsNew(modelMapper.map(signupDto, User.class)), UserDto.class);
	    		
	    		primaryCurrencyProxy.setPrimaryCurrency(new PrimaryCurrencyDto(new CurrencyDto(1),userDto.getId()));

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
	
	

	@RequestMapping(value="users/login",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginDto loginDto, BindingResult result, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	if(loginDto == null) {
				response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else if (result.hasFieldErrors()) {
				response.put("message", result.getFieldError().getDefaultMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else {
	    		Map<String, Object> data = new HashMap<String, Object>();
	    		User user = userService.findByEmail(loginDto.getEmail());
	    		
	    		if(user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
	    			response.put("message", messageSource.getMessage("message.account.login.error", null, Locale.forLanguageTag("en-US")));
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	    		}
	    		
	    		UserDto userDto = modelMapper.map(user, UserDto.class);
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
	
	@RequestMapping(value="users/socialLogin",method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> socialLogin(@RequestBody @Valid SocialLoginDto socialLoginDto, BindingResult result, HttpServletRequest request) {
	    Map<String, Object> response = new HashMap<String, Object>();
	    try {
	    	if(socialLoginDto == null) {
				response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else if (result.hasFieldErrors()) {
				response.put("message", result.getFieldError().getDefaultMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else {
	    		Map<String, Object> data = new HashMap<String, Object>();
	    		SocialAccount socialAccount = socialAccountService.findBySocialId(socialLoginDto.getSocialId());
	    		if(socialAccount==null) {
	    			SocialAccount socialAccount2 = socialAccountService.saveAsNew(socialLoginDto);
	    			User user = socialAccount2.getAppUser();
	    			Set<SocialAccount> socialAccounts = user.getSocialAccounts();
	    			socialAccounts.add(socialAccount2);
	    			user.setSocialAccounts(socialAccounts);
	    			user = userService.merge(user);
	    			
	    			final String token = jwtTokenUtil.generateTokenBySocialId(socialAccount2);
		            data.put("access_token", token);
		    		data.put("user", modelMapper.map(user, UserDto.class));
	    		} else {
	    			final String token = jwtTokenUtil.generateTokenBySocialId(socialAccount);
		            data.put("access_token", token);
		            data.put("user", modelMapper.map(socialAccount.getAppUser(), UserDto.class));
	    		}
	    	
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
	
	@RequestMapping(value="users/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> createPasword(@PathVariable("id") UUID id,
			@RequestBody @Valid CreateProfileDto createProfileDto,  BindingResult result) {
		Map<String, Object> response= new HashMap<String, Object>();
		try {
			if(createProfileDto == null) {
				response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else if (result.hasFieldErrors()) {
				response.put("message", result.getFieldError().getDefaultMessage());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    	} else {
	    		User user = userService.findById(id);
				if(user == null) {
					response.put("message","User id does not exist");
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
				
				String verifictionCode ="0000";	
				 /*String.format("%04d",new Random().nextInt(10000));*/
				Message message  = twillioService.sendCustomOtp(createProfileDto.getCountryCode(),createProfileDto.getPhoneNumber(),verifictionCode);
				if(message.getStatus()!= Message.Status.FAILED) {
					user.setOtp(verifictionCode);
					user = userService.updateProfile(createProfileDto, user);
					
					response.put("data",new HashMap<>());
					response.put("message","A verification code is sent at +"+user.getCountryCode()+
							user.getPhoneNumber());
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.put("message","Error while sending a verification code.");
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message","Error while sending verification code.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="users/{id}/verifyOtp", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> checkOtp(@PathVariable("id") UUID id, @RequestBody VerifyOtpDto verifyOtpDto, BindingResult result) {
		Map<String, Object> response= new HashMap<String, Object>();
		
		User user = userService.findById(id);
		if(user == null) {
			response.put("message","Customer id does not exist");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if(user.getOtp().equals(verifyOtpDto.getOtp())) {
			user.setMobileVerified(true);
			userService.save(user);
			Map<String, Object> data= new HashMap<String, Object>();
			
			response.put("data",data);
			response.put("message","Mobile number verified successfully.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("message", "Verification code is not valid.");
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	@RequestMapping(value="users/forgotPassword",method=RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam("email") String email,HttpServletRequest request){
    	Map<String, Object> response = new HashMap<String, Object>();
    	try {
    		User user = userService.findByEmail(email);
    		if(user != null) {
    			if(user.getPassword() == null) {
    				response.put("message", messageSource.getMessage("message.user.social_account.error", null, Locale.forLanguageTag("en-US")));
    	    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    			}
    			
        		Map<String, Object> mailData = new HashMap<String, Object>();
        		mailData.put("urlLink", "http://192.168.2.58:8001/FilmThere/v1/api/users/"+user.getId()+"/resetPassword?key="+user.getPassword());

        		emailService.sendResetPasswordMail(new Mail(user.getEmail(),"Forgot Pasword Email",mailData));
    			response.put("message", messageSource.getMessage("message.user.forgotpass", null, Locale.forLanguageTag("en-US")));
	    		return new ResponseEntity<>(response, HttpStatus.OK);	
    		} else {
	    		response.put("message", messageSource.getMessage("message.user.register.error", null, Locale.forLanguageTag("en-US")));
	    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		response.put("message", messageSource.getMessage("message.user.forgotpass.error", null, Locale.forLanguageTag("en-US")));
    		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "users/{id}/resetPassword", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("id") UUID id,@RequestParam("key") String key,Model model) {
    	User user = userService.findById(id);
    	if(user.getPassword().equals(key)) {
    		model.addAttribute("email", user.getEmail());
    		model.addAttribute("id", user.getId());
			return  "/reset_password/ResetPassword";
    	} else {
    		model.addAttribute("status",HttpStatus.NOT_FOUND);
			model.addAttribute("message","Link Expired");
			return  "/reset_password/error";
    	}
    }
    
    @RequestMapping(value = "users/{id}/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable("id") UUID id,@RequestBody UpdatePasswordDto updatePasswordDto){
    	Map<String, Object> response = new HashMap<String, Object>();
    	User user = userService.findById(id);
    	if(user.getEmail().equals(updatePasswordDto.getKey())) {
    		user.setPassword(updatePasswordDto.getPassword());
     		userService.save(user);
    		response.put("message", messageSource.getMessage("message.user.password.update", null, Locale.forLanguageTag("en-US")));
    		return new ResponseEntity<>(response, HttpStatus.OK);	
    	} else {
    		response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
    		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);	
    	}
    }
    
    @RequestMapping(value = "users/{id}/changePassword", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> changePassword(@PathVariable("id") UUID id,@RequestBody @Valid ChangePasswordDto changePasswordDto, BindingResult result){
    	Map<String, Object> response = new HashMap<String, Object>();
    	User user = userService.findById(id);
    	//if(passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
    	if(changePasswordDto == null) {
			response.put("message", messageSource.getMessage("message.bad_request", null, Locale.forLanguageTag("en-US")));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    	} else if (result.hasFieldErrors()) {
			response.put("message", result.getFieldError().getDefaultMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    	} else {
    		if(passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
        		user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
         		userService.save(user);
        		response.put("message", messageSource.getMessage("message.change_password", null, Locale.forLanguageTag("en-US")));
        		return new ResponseEntity<>(response, HttpStatus.OK);	
        	} else {
        		response.put("message", messageSource.getMessage("message.change_password.error", null, Locale.forLanguageTag("en-US")));
        		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);	
        	}	
    	}
    }
    @PostMapping(value = "currency-exchange")
    public SignupDto changePassword(@RequestBody SignupDto signupDto){
    	return signupDto;
    }
	
	
}
