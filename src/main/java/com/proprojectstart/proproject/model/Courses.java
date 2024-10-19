package com.proprojectstart.proproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="courses")
public class Courses {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column
private int id;
@Column
private String coursename;
@Column
private String coursedetails;
@Column
private String courseduration;
@Column
private String coursefee;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCoursename() {
	return coursename;
}
public void setCoursename(String coursename) {
	this.coursename = coursename;
}
public String getCoursedetails() {
	return coursedetails;
}
public void setCoursedetails(String coursedetails) {
	this.coursedetails = coursedetails;
}
public String getCourseduration() {
	return courseduration;
}
public void setCourseduration(String courseduration) {
	this.courseduration = courseduration;
}
public String getCoursefee() {
	return coursefee;
}
public void setCoursefee(String coursefee) {
	this.coursefee = coursefee;
}
}