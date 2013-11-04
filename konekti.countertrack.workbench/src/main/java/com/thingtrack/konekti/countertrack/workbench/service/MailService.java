package com.thingtrack.konekti.countertrack.workbench.service;

import javax.mail.MessagingException;

public interface MailService {
	public void sendEmailTLS(String subject, String message) throws MessagingException;
	public void sendEmailTLS(String subject, String message, String emailTo) throws MessagingException;
	public void sendEmailSSL(String subject, String message) throws MessagingException;
	public void sendEmailSSL(String subject, String message, String emailTo) throws MessagingException;
	
}
