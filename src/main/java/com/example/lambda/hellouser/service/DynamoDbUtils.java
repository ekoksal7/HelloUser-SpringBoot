package com.example.lambda.hellouser.service;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Component
public class DynamoDbUtils {
	
	private String DYNAMODB_TABLE_NAME = "HelloUser";
	private DynamoDB dynamoDb;
    private Regions REGION = Regions.EU_CENTRAL_1;
    private AmazonDynamoDBClient client;
    
    
    public DynamoDbUtils() {
    	initDynamoDbClient();
    }
    
    private void initDynamoDbClient() {
    	client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }

	public DynamoDB getDynamoDb() {
		return dynamoDb;
	}

	public void setDynamoDb(DynamoDB dynamoDb) {
		this.dynamoDb = dynamoDb;
	}

	public String getDYNAMODB_TABLE_NAME() {
		return DYNAMODB_TABLE_NAME;
	}

	public void setDYNAMODB_TABLE_NAME(String dYNAMODB_TABLE_NAME) {
		DYNAMODB_TABLE_NAME = dYNAMODB_TABLE_NAME;
	}
	
}
