package com.proprojectstart.proproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ContactService {
@Autowired
		public JavaMailSender mailsender;
				public void sendContact(String sess,String email,String subject,String message) throws MessagingException {
					MimeMessage mimemessage=mailsender.createMimeMessage();
					MimeMessageHelper helper=new MimeMessageHelper(mimemessage,true);
						String mess="<b>From:[[From]]</b><br><b>To:[[To]]</b><br><b>message:[[message]]</b>";
							mess=mess.replace("[[From]]", email);
							mess=mess.replace("[[To]]", sess);
							mess=mess.replace("[[[subject]]",subject);
							mess=mess.replace("[[message]]", message);
								helper.setFrom(sess);
								helper.setTo(sess);
								helper.setSubject(subject);
								helper.setText(mess,true);
								mailsender.send(mimemessage);
				}
}
