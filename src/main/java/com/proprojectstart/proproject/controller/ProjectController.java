package com.proprojectstart.proproject.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proprojectstart.proproject.model.City;
import com.proprojectstart.proproject.model.Contact;
import com.proprojectstart.proproject.model.Country;
import com.proprojectstart.proproject.model.Courses;
import com.proprojectstart.proproject.model.ProjectModel;
import com.proprojectstart.proproject.model.State;
import com.proprojectstart.proproject.respository.CountryRepository;
import com.proprojectstart.proproject.respository.CourseRepository;
import com.proprojectstart.proproject.respository.ProjectRepository;
import com.proprojectstart.proproject.service.CityService;
import com.proprojectstart.proproject.service.ContactService;
import com.proprojectstart.proproject.service.CountryService;
import com.proprojectstart.proproject.service.ProjectService;
import com.proprojectstart.proproject.service.StateService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService regform;
	@Autowired
	private ProjectRepository repo;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateService stateService;
	@Autowired
	private CityService cityservice;
	@Autowired
	private CountryService countryservice;
	@Autowired
	private CourseRepository courserepo;
	@Autowired
	private ContactService contactservice;
@RequestMapping("form")
		public String form(Model m) {
		m.addAttribute("form",new ProjectModel());
		m.addAttribute("countryModel", countryservice.countryList());
	return"register";
}
@RequestMapping("save")
		public String save(@ModelAttribute("form")ProjectModel form) {
				regform.saveForm(form);
				return "redirect:/form";
}
@RequestMapping("/send")
		public String send(ProjectModel user,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
					regform.register(user, getSiteURL(request));
						return"check";
}
//@RequestMapping("/send")
//public String send(@ModelAttribute("user") ProjectModel user, 
//                   HttpServletRequest request,
//                   @RequestParam("image") MultipartFile file,
//                   BindingResult result,
//                   Model model) throws MessagingException, IOException {
//    // Validate file selection
//    if (file.isEmpty()) {
//        result.rejectValue("file", "error.file", "Please select an image to upload");
//    }
//
//    if (result.hasErrors()) {
//        return "register";
//    } else {
//        // Register the user and handle file saving
//        regform.register(user, getSiteURL(request), file);
//        model.addAttribute("form", user);
//        return "register";
//    }
//}
private String getSiteURL(HttpServletRequest request) {
				String siteurl=request.getRequestURL().toString();
				return siteurl.replace(request.getServletPath(),"");
				
}
@RequestMapping("/verify")
public String verify(@Param("code")String code,Model m) {
	if(regform.verify(code)) {
		m.addAttribute("form",new ProjectModel());
		return "login";
	}
	else {
		return "error";
	}
		
	}
public String generateRandomOtp() {
	String otp=String.valueOf(new Random().nextInt(900000)+100000);
	
	return otp;
}

@RequestMapping("/send-otp")
				public String sendOtp(Model m,@RequestParam("email") String email,ProjectModel student) throws UnsupportedEncodingException, MessagingException {
							ProjectModel existingStudent=repo.findByEmail(email);
							
							if(existingStudent !=null) {
								String newOtp=generateRandomOtp();
								
								System.out.println(newOtp);
								existingStudent.setOtp(newOtp);
								repo.save(existingStudent);
								regform.sendingOtp(email,student,newOtp);
								
								
								m.addAttribute("form",new ProjectModel());
								System.out.println(email);
								m.addAttribute("email",email);
								return "verifyotp";
							}
							else {
								m.addAttribute("error","Email not found");
								return "register";
							}
	
}
@RequestMapping("/verify-otp")
//			public String verifyOTP(@RequestParam("email") String email,@RequestParam("enteredOtp") String enteredOtp,Model model) {
//								ProjectModel student=repo.findByEmail(email);
//										System.out.println(email);
//								System.out.println(enteredOtp);
//								//System.out.println(student.getOtp());
//								
//								if(student!=null&&student.getOtp()!=null&&student.getOtp().equals(enteredOtp)) {
//									student.setVerified(true);
//									repo.save(student);
//									model.addAttribute("email",email);
//									
//									return "setnewpassword";
//									
//								}else {
//									model.addAttribute("error","invalid OTP.please try again");
//									model.addAttribute("form",new ProjectModel());
//									return "verifyotp";
//								}
					public String verifyOtp(@RequestParam("enteredOtp")String enteredOtp,@RequestParam("email") String email,Model m ) {
		
					if(regform.verifyOotp(enteredOtp)) {
						m.addAttribute("form",new ProjectModel());
						System.out.println("enteredotp:"+enteredOtp);
						System.out.println("email="+email);
						m.addAttribute("email",email);
						return "setPassword";
						
					}
					else {						
						m.addAttribute("error","invalid OTP.please try again");
						m.addAttribute("form",new ProjectModel());
					return "verifyotp";
				}
}
@RequestMapping("/password")
			public String settPassword(@RequestParam("email") String email,@RequestParam("newpassword")String newpassword,@RequestParam("conformpassword")String conformpassword,Model m) {
						ProjectModel existingStudent=repo.findByEmail(email);
						System.out.println("email="+email);
						System.out.println(newpassword);
						System.out.println(conformpassword);
						System.out.println("========CHECK METHOD=================");
					if(existingStudent!=null&&newpassword.equals(conformpassword)) {
//								String pa=newpassword;
//								existingStudent.setPassword(pa);
						System.out.println("==========check 1==============");
								regform.passwordSet(newpassword,email);
								System.out.println("==========check 2==============");
								return "home";
					}
					
					else {
						m.addAttribute("form",new ProjectModel());
						return "setPassword";
						
					}
}
@RequestMapping("/loogin")
	public String loogin(Model m) {
		m.addAttribute("login",new ProjectModel());
			return("loogin");
	}

@RequestMapping("/login")
			public String login(@RequestParam("email") String email,@RequestParam("enterPassword") String password,@RequestParam(value="KeepLoggedIn",required = false) String KeepLoggedIn,Model m,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
							ProjectModel loginStudent=repo.findByEmail(email);
							if(loginStudent!=null&&email.equals(loginStudent.getEmail())&&password.equals(loginStudent.getPassword())) {
											loginStudent.setLogin(true);
											repo.save(loginStudent);
									session.setAttribute("student", loginStudent.getEmail());
//								Cookie [] cookie=request.getCookies();
//								if(cookie!=null) {
//										for(Cookie cookies:cookie) {
//											if("student".equals(cookies.getName())) {
//												cookies.setMaxAge(60);
//												response.addCookie(cookies);
//												m.addAttribute("student",cookies.getValue());
//												List<ProjectModel> students = getAllStudents();
//										        m.addAttribute("students", students);
//												return "redirect:/dashboard";
//											}
//										}
//								}
									Cookie cookies = new Cookie("student", loginStudent.getEmail());
									if("true".equals(KeepLoggedIn)) {
										cookies.setMaxAge(10);
										
						            } else {
						               
						            	 session.setMaxInactiveInterval(0);
						            }

									
									
//								Cookie cookies=new Cookie("student",loginStudent.getEmail());
								//studentCookie.setMaxAge(60);
								cookies.setPath("/");
								cookies.setHttpOnly(true);
								cookies.setSecure(true);
								response.addCookie(cookies);
								m.addAttribute("form",new ProjectModel());

								return"redirect:/dashboard";
							}
							else {
								return"error";
							}
							
			}
public List<ProjectModel> getAllStudents() {
    return regform.getAllStudents();
}
@RequestMapping("/editprofile")
		public String edit(HttpSession session,Model m) {
				String username=(String) session.getAttribute("student");
				ProjectModel stud=repo.findByEmail(username);
				System.out.print("findbyemail=========================================="+username);
				if(username.equals(stud.getEmail())) {
				System.out.print("editprofilesession=========================================="+username);
				List<ProjectModel> students = getAllStudents();
		        m.addAttribute("students", students);

				
				m.addAttribute("form",new ProjectModel());
				
				 
				return "editprofile";

				}
								else {
					return"redirect:/dashboard";
				}
}
@RequestMapping("/profile")
		public String profile(HttpSession session,Model m,ProjectModel user) {
						String stud=(String) session.getAttribute("student");
						ProjectModel student=repo.findByEmail(stud);
						System.out.print("session==================================================="+stud);
						if(student!=null) {
							student.setFirstname(user.getFirstname());
							student.setLastname(user.getLastname());
							student.setPhone(user.getPhone());
							student.setAvatar(user.getAvatar());
							student.setGender(user.getGender());
							student.setCountry(user.getCountry());
							student.setCity(user.getCity());
							student.setState(user.getState());
							student.setDob(user.getDob());
							repo.save(student);
							return"redirect:/dashboard";
						}
						else {
							return "redirect:/editprofile";
						}
}
@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {
    session.setAttribute("student", null);
    Cookie cookie = new Cookie("student", null);
    cookie.setMaxAge(0);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
    	return"redirect:/loogin";
}
@RequestMapping("/showpasswordchange")
			public String changePasswordView(Model m,HttpSession session) {
					String student=(String) session.getAttribute("student");
					if(student !=null) {
						m.addAttribute("email",student);
						System.out.print("resetpassword==============================="+student);
					}else {
				        System.out.println("No student found in session.");
				    }
					
				m.addAttribute("form",new ProjectModel());
				return"resetpassword";
}
@RequestMapping("/reset-password")
			public String resetPassword(@RequestParam("currentpassword")String currentpassword,@RequestParam("conformpassword") String conformpassword,HttpSession session,Model m) {
						String student=(String) session.getAttribute("student");
									m.addAttribute("email",student);
						ProjectModel dpassword=repo.findByEmail(student);
								if(student.equals(dpassword.getEmail())) {
										if(currentpassword.equals(dpassword.getPassword())) {
												dpassword.setPassword(conformpassword);
												repo.save(dpassword);
												return"redirect:/loogin";
													}
										
										if(!currentpassword.equals(dpassword.getPassword())) {
											return"redirect:/showpasswordchange";
										}
									
					}
								return"redirect:/showpasswordchange";
}
@RequestMapping("/forget")
			public String forget(Model m,HttpSession session,@RequestParam("email")String email) {
				String student=(String) session.getAttribute("student");
						if(student.equals(email)) {
						System.out.print("forget================================================"+email);
						m.addAttribute("email",email);
					m.addAttribute("forget",new ProjectModel());
						}
							return"forget";
		}
@RequestMapping("/forgetpassword")
					public String forgetpassword(Model m,@RequestParam("email") String email,ProjectModel student) throws UnsupportedEncodingException, MessagingException {
					ProjectModel existingStudent=repo.findByEmail(email);
					
					
					if(existingStudent !=null) {
						String newOtp=generateRandomOtp();
						
						System.out.println(newOtp);
						existingStudent.setOtp(newOtp);
						repo.save(existingStudent);
						regform.sendingOtp(email,student,newOtp);
						
						m.addAttribute("email",email);
						m.addAttribute("form",new ProjectModel());
						return "redirect/verify-otp";
					}
					else {
						m.addAttribute("error","Email not found");
						return "register";
					}
				
				}
				
							
									
								
@RequestMapping("/dashboard")
			public String StudentDashboard(@CookieValue(value="student",required=false)String cook,Model m,HttpServletRequest request,HttpSession sessions) {
//								String session=(String) sessions.getAttribute("student");
//								System.out.print("session=================================="+session);
//								if(cook!=null||session !=null) {
//									ProjectModel std=repo.findByEmail(session);
//									m.addAttribute("stud",std);
//								}
						Cookie[] coookie=request.getCookies();
						if(coookie!=null){
									for(Cookie cookie:coookie ) {
							if("student".equals(cookie.getName())) {
								ProjectModel student=repo.findByEmail(cookie.getValue());
									if(student !=null) {
										List<ProjectModel> students = getAllStudents();
								        m.addAttribute("students", students);
										m.addAttribute("student",student.getEmail());
										return "dashboard";
									}
							}
						}
				}
						
				return "loogin";		
	}
@GetMapping("/countries")
			public List <Country> getCountries(){
					return countryRepository.findAll();
		}
@GetMapping("/states/{countryid}")
			public @ResponseBody Iterable<State>getStateByCountry(@PathVariable("countryid") Country countryid){
					return stateService.geStateBy(countryid);
			}
@GetMapping("/cities/{stateid}")
		public @ResponseBody Iterable <City> getCityByState(@PathVariable("stateid") State stateid){
						  
					return cityservice.getCityBy(stateid);
}
@RequestMapping("/course")
		public String course() {
				return"course";
}
@RequestMapping("/home")
				public String home() {
				return "home";
}			

@RequestMapping("/course/{id}")
		public String java(Model m,@PathVariable("id")int id) {
				Courses cou=courserepo.getById(id);
						String a=cou.getCoursefee();
						String b=cou.getCoursedetails();
						System.out.print("fee========================================"+b);
						m.addAttribute("details",cou);
					return"Coursedetails";
	}
@RequestMapping("/joinnow/{id}")
			public String joinnow(HttpSession session,@PathVariable("id")int id,Model m,RedirectAttributes redirectAttributes) {
								System.out.print("int============================================"+id);
						String Student=(String) session.getAttribute("student");
						ProjectModel existingStudent=repo.findByEmail(Student);
						Courses cou=courserepo.getById(id);
						if(existingStudent.isLogin()) {
							existingStudent.setCourse(id);
							repo.save(existingStudent);
							redirectAttributes.addFlashAttribute("course","you joined the"+cou.getCoursename()+"course");
							return"redirect:/home";
						}else {
							return"redirect:/form";
						}
						
						
						
						
	}
@RequestMapping("/contact")
			public String contact(Model m,HttpSession session) {
				m.addAttribute("contact",new Contact());
				session.setAttribute("admin", "ashikaj888@gmail.com");
				
				return"contact";
		}

@RequestMapping("/contactsend")
			public String submitmail(Contact contact,HttpSession session,Model m,RedirectAttributes redirectAttributes) throws MessagingException {
						String sess=(String) session.getAttribute("admin");
						String name=contact.getName();
						String email=contact.getEmail();
						String message=contact.getMessage();
						String subject=contact.getSubject();
						contactservice.sendContact(sess, email, subject, message);
					    redirectAttributes.addFlashAttribute("message", "Your contact has been sent successfully!");

						System.out.print(name+email+message+subject+sess);
						
						
						
						 return "redirect:/contact";
}


}	
		


//@RequestMapping("save")
//		public String save( @ModelAttribute("form")ProjectModel form,Model m,HttpServletResponse response) {
//		/*if(result.hasErrors()) {
//			return("register");
//	
//		}*/
//		Cookie cookie =new Cookie("user",form.getFirstname());
//		cookie.setMaxAge(7*24*60*60);
//		cookie.setPath("/");
//		response.addCookie(cookie);
//		
//		regform.saveForm(form);
//		return "redirect:/home";
//}
//@RequestMapping("home")
//		public String homepage(HttpServletRequest request,Model m) {
//		Cookie [] Cookies=request.getCookies();
//		if(Cookies!=null) {
//			for(Cookie cookie:Cookies) {
//				if("user".equals(cookie.getName())) {
//				String username=cookie.getValue();
//				m.addAttribute("user",username);
//					if("Ashikaj".equals(username)) {
//						return"demo";
//					}else {
//						return "redirect:/form";
//						
//								}
//				
//						}
//				}	
//	
//		
//		}
//		return "redirect:/form";
//	}
