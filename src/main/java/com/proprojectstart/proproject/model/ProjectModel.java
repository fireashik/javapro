package com.proprojectstart.proproject.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="verifytable")
public class ProjectModel {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="id",length=10)
private int id;
@Column
private String firstname;
@Column
private String lastname;
@Column
private LocalDate dob;
@Column
private String gender;
@Column 
@Email
private String email;
@Column
private String phone;
@Column
private String country;
@Column 
private String state;
@Column 
private String city;
@Column
private String hobbies;
@Column
private String avatar;
@Column
private String verificationcode;
@Column
private boolean enabled;
@Column
private String otp;
@Column 
private String password;
@Column
private boolean login;
@Column
private  Integer course;
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Column
private boolean verified;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public LocalDate getDob() {
	return dob;
}
public void setDob(LocalDate dob) {
	this.dob = dob;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getHobbies() {
	return hobbies;
}
public void setHobbies(String hobbies) {
	this.hobbies = hobbies;
}
public String getAvatar() {
	return avatar;
}
public void setAvatar(String avatar) {
	this.avatar = avatar;
}

public String getVerificationcode() {
	return verificationcode;
}
public void setVerificationcode(String verificationcode) {
	this.verificationcode = verificationcode;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getOtp() {
	return otp;
}
public void setOtp(String otp) {
	this.otp = otp;
}
public boolean isVerified() {
	return verified;
}
public void setVerified(boolean verified) {
	this.verified = verified;
}
public boolean isLogin() {
	return login;
}
public void setLogin(boolean login) {
	this.login = login;
}
public Integer getCourse() {
	return course;
}
public void setCourse(Integer course) {
	this.course = course;
}

}
