package com.example.lambda.hellouser.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.example.lambda.hellouser.model.HelloRequest;

@Service
public class HelloUserServiceImpl implements HelloUserService {
	
	private static final String DATE_FORMAT="yyyy-MM-dd";

	@Autowired
	private DynamoDbUtils dynamoDbUtils;
	
	@Override
	public void saveUser(HelloRequest request) {
		
		persistData(request);

	}

	@Override
	public HelloRequest getUser(String user) throws Exception {
		
		return findUser(user);
	}
	
	private PutItemOutcome persistData(HelloRequest request) 
		      throws ConditionalCheckFailedException {
	    return this.dynamoDbUtils.getDynamoDb().getTable(dynamoDbUtils.getDYNAMODB_TABLE_NAME())
	      .putItem(
	        new PutItemSpec().withItem(new Item()
	          .withString("first_name", request.getUser())
	          .withString("birth_date", new SimpleDateFormat(DATE_FORMAT).format(request.getDateOfBirth()))));
	}
	
	
	private HelloRequest findUser(String user) throws Exception{
		
		HelloRequest helloRequest=null;		

		QuerySpec spec = new QuerySpec()
		    .withKeyConditionExpression("first_name = :fn")
		    .withValueMap(new ValueMap()
			        .withString(":fn", user)).withConsistentRead(true);
		
		
		ItemCollection<QueryOutcome> items = dynamoDbUtils.getDynamoDb().getTable(dynamoDbUtils.getDYNAMODB_TABLE_NAME()).query(spec);

		 if(items!=null && items.iterator()!=null && items.iterator().hasNext()) {
			 
		 	Item item=items.iterator().next();
			
			String birtDateString=item.getString("birth_date");
			Date birthDate=new SimpleDateFormat(DATE_FORMAT).parse(birtDateString);
			
			helloRequest = new HelloRequest(birthDate,user);
				
		 }
		 
		 return helloRequest;
	}

}
