package com.efx.user.services.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import com.efx.user.services.IEmailService;
import com.efx.user.services.Mail;

@Service
public class EmailService implements IEmailService {
	
	@Autowired
	 private JavaMailSender emailSender;

	 @Autowired
	 private SpringTemplateEngine templateEngine;

	 @Value("${mail.send-from}")
	 private String fromMail;

	 @Override
	 public void sendResetPasswordMail(Mail mail) throws MessagingException, IOException {
       try {
    	   System.out.println("Callled this method");
		   MimeMessage message = emailSender.createMimeMessage();
		   MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
	        Context context = new Context();
	        context.setVariables(mail.getModel());
	        String html = templateEngine.process("reset_password/PasswordRecoveryMail", context);
	
	        helper.setTo(mail.getTo());
	        helper.setText(html, true);
	        helper.setSubject(mail.getSubject());
	        helper.setFrom(fromMail);
	        emailSender.send(message);
       } catch (Exception e) {
			e.printStackTrace();
		}
	 }

}

