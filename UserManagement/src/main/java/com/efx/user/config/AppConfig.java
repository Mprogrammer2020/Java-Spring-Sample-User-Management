package com.efx.user.config;

import java.util.Locale;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/users/**").addResourceLocations("file:EfxUserManagement/resources/images/users/");
	}

	@Bean
	public LocaleResolver localeResolver() {
       SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
       sessionLocaleResolver.setDefaultLocale(Locale.US);
       return sessionLocaleResolver;
	}
	
	@Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
       LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
       lci.setParamName("lang");
       return lci;
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setPassword("netsetiso1901");
		javaMailSender.setUsername("netsetsoftwareiso@gmail.com");
		javaMailSender.setJavaMailProperties(getMailProperties());
		return javaMailSender;
	}
	
	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.debug", "false");
		return properties;
	}
}
