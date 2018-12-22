package com.example.lambda.hellouser.model;

import java.util.Date;

public class HelloRequest {
	
	private Date dateOfBirth;
	private String user;
	
	public HelloRequest() {
		
	}
	public HelloRequest(Date dateOfBirth,String user) {
		this.dateOfBirth=dateOfBirth;
		this.user=user;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}
