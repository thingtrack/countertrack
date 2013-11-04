package com.thingtrack.konekti.countertrack.workbench.service;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailServiceImpl implements MailService {
	private static final String USERNAME = "info@thingtrack.com";
	private static final String PASSWORD = "B33982307";
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "465";
	
	private static final String SOCKET_PORT = "465";
	private static final String CLASS = "javax.net.ssl.SSLSocketFactory";	
	private static final String AUTH = "true";
		
	private static final String START_TTLS = "true";

	private static final String EMAIL_FROM = "info@thingtrack.com";
	private static final String EMAIL_TO = "info@thingtrack.com";
	//private static final String EMAIL_TO = "miguel@thingtrack.com";
	
	public void sendEmailTLS(String subject, String payload) throws MessagingException {
		sendEmailTLS(subject, payload, EMAIL_TO);
		
	}
	
	public void sendEmailTLS(String subject, String payload, String emailTo) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.starttls.enable", START_TTLS);
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
		
		// Create multipart message.  
		MimeMultipart multipart = new MimeMultipart();  
		
		// Create bodypart.  
		BodyPart bodyPart = new MimeBodyPart();  		
		bodyPart.setContent(payload, "text/html");  
		
		// Add the HTML bodypart to the multipart.  
		multipart.addBodyPart(bodyPart);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(EMAIL_FROM));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
		message.setSubject(subject);
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	public void sendEmailSSL(String subject, String payload) throws MessagingException {
		sendEmailSSL(subject, payload, EMAIL_TO);
		
	}
	
	public void sendEmailSSL(String subject, String payload, String emailTo) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.socketFactory.port", SOCKET_PORT);
		props.put("mail.smtp.socketFactory.class", CLASS);		
		props.put("mail.smtp.auth", AUTH);		
		props.put("mail.smtp.port", PORT);
				
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
		
		// Create multipart message.  
		MimeMultipart multipart = new MimeMultipart();  
		
		// Create bodypart.  
		BodyPart bodyPart = new MimeBodyPart();  		
		bodyPart.setContent(payload, "text/html");  
		
		// Add the HTML bodypart to the multipart.  
		multipart.addBodyPart(bodyPart);
				
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(EMAIL_FROM));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
		message.setSubject(subject);
		message.setContent(multipart);
		
		Transport.send(message);
	}
		
}
