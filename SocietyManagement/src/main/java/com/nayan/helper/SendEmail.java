package com.nayan.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail{

	@Autowired
	private JavaMailSender mailer;
	
	private String to;
	private String subject;
	private String body;
	private final String from="ldcesociety@gmail.com";
	public SendEmail() {
		super();
	}
	public SendEmail(String to, String subject, String body) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public boolean send() {
		if(this!=null) {
			SimpleMailMessage msg=new SimpleMailMessage();
			msg.setFrom(from);
			msg.setTo(to);
			msg.setSubject(subject);
			msg.setText(body);
			try {
				System.out.println(mailer);
				mailer.send(msg);
				return true;
			}catch(MailException ex) {
				ex.printStackTrace();
			}
			
		}
		return false;
	}
	
	

}
