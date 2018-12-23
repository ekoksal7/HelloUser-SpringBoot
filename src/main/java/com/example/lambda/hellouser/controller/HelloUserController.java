package com.example.lambda.hellouser.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.lambda.hellouser.model.HelloRequest;
import com.example.lambda.hellouser.model.HelloResponse;
import com.example.lambda.hellouser.service.HelloUserService;

@RestController
public class HelloUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloUserController.class);

	@Autowired
	private HelloUserService helloUserService;
	
	@PutMapping("/hello/{user}")
	public @ResponseBody ResponseEntity<Void> saveUser(@PathVariable("user") String user, 
			                                           @RequestBody HelloRequest request) {
		LOGGER.info("saveUser: user={}, birtdate={}", user,request.getDateOfBirth());
		
		request.setUser(user);
		helloUserService.saveUser(request);
		
		try {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
	}
	
	@GetMapping("/hello/{user}")
	public @ResponseBody ResponseEntity<HelloResponse> getUser(@PathVariable("user") String user) {
		LOGGER.info("getUser: user={}", user);		
		
		HelloRequest request=null;
		
		try {
			request = helloUserService.getUser(user);
			
			if(request==null) {
	    		return new ResponseEntity<HelloResponse>(HttpStatus.NOT_FOUND);
	    	}
	    	else {
	    		return new ResponseEntity<HelloResponse>(createHelloResponse(request),HttpStatus.OK);
	    	}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new ResponseEntity<HelloResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	
	}
	
	@GetMapping("/health")
	public @ResponseBody ResponseEntity<Void> health() {
		LOGGER.info("get health ");		
		
		return new ResponseEntity<Void>(HttpStatus.OK);
    	
    	
	}
	
	private HelloResponse createHelloResponse(HelloRequest request) {
		
		
		Date today=new Date();
		int dayDiff=getDayDiff(request.getDateOfBirth(),today);
		String message="";
		if(dayDiff==0) {
			message="Hello, "+request.getUser()+"! Happy birthday!";
		}
		else {
			message="Hello, "+request.getUser()+"! Your birthday is in "+dayDiff+" days";
		}
		
		return new HelloResponse(message);
	}
	private int getDayDiff(Date d1,Date d2) {
		int dayDiff=0;
		
		if(d1.getDate()!=d2.getDate() || d1.getMonth()!=d2.getMonth()) {
		
			d1.setYear(d2.getYear());
			long diff=0;
			
			
			diff = d1.getTime() - d2.getTime();
			
			dayDiff=new Long(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)).intValue();
			
			if(dayDiff<0)
				dayDiff=365+dayDiff;
		}
	    return dayDiff;
	}
}
