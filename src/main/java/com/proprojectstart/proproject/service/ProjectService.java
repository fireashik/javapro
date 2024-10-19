package com.proprojectstart.proproject.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proprojectstart.proproject.model.ProjectModel;
import com.proprojectstart.proproject.respository.ProjectRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.Path;
import net.bytebuddy.utility.RandomString;

@Service
public class ProjectService {
	@Autowired
	private ProjectRepository formrepo;
	@Autowired
		private JavaMailSender sender;
		
		public void saveForm(ProjectModel form) {
			// TODO Auto-generated method stub
			formrepo.save(form);
			
		}
		 public List<ProjectModel> getAllStudents() {
		        return (List<ProjectModel>) formrepo.findAll();  // Retrieves all records from the table
		    }
		
		public void register(ProjectModel user,String siteUrl) throws UnsupportedEncodingException, MessagingException {
						String randomCode=RandomString.make(64);
						user.setVerificationcode(randomCode);
						user.setEnabled(false);
						formrepo.save(user);
						sendVerificationEmail(user,siteUrl);
		}
//		 private String folder="/proproject/src/main/resources/static/img";
//
//		 public void register(ProjectModel user, String siteUrl, MultipartFile file) throws IOException, MessagingException {
//			    java.nio.file.Path uploadPath = Paths.get(folder);
//			    if (!Files.exists(uploadPath)) {
//			        Files.createDirectories(uploadPath);
//			    }
//			    		
//			    String filename = file.getOriginalFilename();
//			    java.nio.file.Path path = uploadPath.resolve(filename);
//			    Files.write(path, file.getBytes());
//			    user.setAvatar(path.toString());
//
//			  
//			    String randomCode = RandomString.make(64);
//			    user.setVerificationcode(randomCode);
//			    user.setEnabled(false);
//			    formrepo.save(user);
//			    sendVerificationEmail(user, siteUrl);
//			}
		public void sendVerificationEmail(ProjectModel user,String siteUrl) throws UnsupportedEncodingException, MessagingException {
			String toaddr=user.getEmail();
			String fromaddr="ashikaj888@gmail.com";
			String senderName="Ashik";
			String subject="Verify REgistration";
			String message="Dear [[name]] please click below link to verify <h3><a href=\"[[URL]]\" target=\"_blank\">verify</a></h3>";
			MimeMessage msg=sender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(msg);
			
					messageHelper.setFrom(fromaddr,senderName);
					messageHelper.setTo(toaddr);
					messageHelper.setSubject(subject);
					message=message.replace("[[name]]",user.getFirstname());
					String url=siteUrl+"/verify?code="+user.getVerificationcode();
					message=message.replace("[[URL]]",url);
					messageHelper.setText(message,true);
					sender.send(msg);
		}
			public boolean verify(String verificationcode) {
							ProjectModel user=formrepo.findByVerificationcode(verificationcode);
									if(user==null||user.isEnabled()) {
												return false;
												
									}else {
										user.setVerificationcode(null);
										user.setEnabled(true);
										formrepo.save(user);
										return true;
									}
			}
			
					public void sendingOtp(String email,ProjectModel m,String otp) throws UnsupportedEncodingException, MessagingException {
									String toaddr=m.getEmail();
									String fromaddr="ashikaj888@gmail.com";
									String senderName="Scope";
									String subject="verify OTP";
									String message="[[otp]]";
									MimeMessage msg=sender.createMimeMessage();
									MimeMessageHelper helper=new MimeMessageHelper(msg);
									
											helper.setFrom(fromaddr,senderName);
											helper.setTo(toaddr);
											helper.setSubject(subject);
											
											message=message.replace("[[otp]]",otp);
						
											helper.setText(message,true);
											m.setOtp(otp);
											
											sender.send(msg);
											 
										    
					}
					public boolean verifyOotp(String otp) {
						ProjectModel user=formrepo.findByOtp(otp);
						if(user!=null&&user.getOtp()!=null&&user.getOtp().equals(otp)) {
							user.setOtp(null);
							user.setVerified(true);
							formrepo.save(user);
							return true;
						}
						else {
							
							return false;
						}
							
					}
					public void passwordSet(String newpassword,String email) {
							ProjectModel user=formrepo.findByEmail(email);
							if(user!=null&&user.isEnabled()&&user.isVerified()) {
								System.out.println("checking-----------");
								user.setPassword(newpassword);
								formrepo.save(user);
							}
							else {
								System.out.println("unable to set password");
							}
						
					}
}
