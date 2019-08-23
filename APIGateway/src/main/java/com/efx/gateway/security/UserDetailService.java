package com.efx.gateway.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.efx.gateway.dao.IUserDao;
import com.efx.gateway.models.SocialAccount;
import com.efx.gateway.models.User;

@Component
public class UserDetailService implements UserDetailsService {

	@Autowired
	IUserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("User name >>> "+username);
		User user = userDao.findByEmail(username);
        if (user == null) {
        	SocialAccount socialAccount = userDao.findBySocialId(username);
        	if(socialAccount!=null) {
        		return new JwtUser(socialAccount.getAppUser().getId(),
        				socialAccount.getAppUser().getEmail(), socialAccount.getAppUser().getPassword(),
        				mapToGrantedAuthorities(socialAccount.getAppUser().getRole().getRole()),
        				socialAccount.getAppUser().getStatus(), socialAccount.getAppUser().getLastPasswordResetDate());
        	} else {
        		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));	
        	}
        } else {
        	return new JwtUser(user.getId(), user.getEmail(),
        			user.getPassword(), mapToGrantedAuthorities(user.getRole().getRole()),
        			user.getStatus(), user.getLastPasswordResetDate());
        }
	}
	
	 private static Set<GrantedAuthority> mapToGrantedAuthorities(String role) {
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	    grantedAuthorities.add(new SimpleGrantedAuthority(role));
    	return grantedAuthorities;
    }

}
