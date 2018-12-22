package com.example.lambda.hellouser.service;

import com.example.lambda.hellouser.model.HelloRequest;

public interface HelloUserService {
	
	public void saveUser(HelloRequest request);
	
	public HelloRequest getUser(String user) throws Exception;

}
