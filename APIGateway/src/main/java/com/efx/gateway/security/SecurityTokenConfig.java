package com.efx.gateway.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity 
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtConfig jwtConfig;

	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Autowired
	private UserDetailService userDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling()
			.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
			.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig,userDetailService), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/EFXUserManagement/v1/api/users","/EFXUserManagement/v1/api/merchants",
					"/EFXUserManagement/v1/api/users/login", "/EFXUserManagement/v1/api/users/socialLogin").permitAll()
			.antMatchers("/EFXUserManagement/v1/api/users/forgotPassword").permitAll()
			.anyRequest().authenticated();
	}

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}
	
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring()
	            .antMatchers(
	                HttpMethod.GET,"/","/*.html",
	                "/favicon.ico",
	                "/**/*.html",
	                "/**/*.jsp",
	                "/**/*.css",
	                "/**/*.js",
	                "/**/*.png",
	                "/**/*.jpg",
	                "/**/*.jpeg",
	                "/**/*.JPG",
	                "/**/*.gif",
	                "/**/*.map",
	                "/fonts/**",
	                "/js/**",
	                "css/**"
	            ).and()
	            .ignoring()
	            .antMatchers("/h2-console/**/**");
	    }
}