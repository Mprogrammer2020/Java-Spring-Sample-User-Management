package com.efx.user.services;

import java.io.IOException;

import javax.mail.MessagingException;

public interface IEmailService {

	void sendResetPasswordMail(Mail mail) throws MessagingException, IOException;
	
}
