# HelloUser API Spring Boot AWS Solution

HelloUser API is developed by Spring Boot framework. This API runs on tomcat web container on EC2 instances. EC2 instances are created from a AMI that has java and tomcat in it. When ec2 instances are started up, it gets the application from github and deploy it to the tomcat by running the below script.

- #!/bin/bash
- cd /home/ec2-user/projects/ 
- rm -rf HelloUser-SpringBoot 
- git clone https://github.com/ekoksal7/HelloUser-SpringBoot.git 
- cp -f HelloUser-SpringBoot/HelloUserService.war /home/ec2-user/apache-tomcat-9.0.8/webapps/ 
- cd /home/ec2-user/apache-tomcat-9.0.8/bin/ 
- ./startup.sh

With the cloud formation template file  **hello-user-service-template.json** in this repository, you can create the aws solution that were shown on **aws-hello-user-service-architecture diagram.png** by running below scripts with aws cli.

- First create a profile
  
    aws configure --profile ekoksal7-eu-central-1

    AWS Access Key ID [None]: <Access Key ID>
    AWS Secret Access Key [None]: <Secret Access Key ID>
    Default region name [None]: **eu-central-1**
    Default output format [None]: json
  
 - Create a key pair for EC2 instances
 
    aws ec2 create-key-pair --profile ekoksal7-eu-central-1 --key-name HelloUserServiceKeyPair
    
 - Create cloud fromation stack
 
    aws cloudformation create-stack --profile ekoksal7-eu-central-1 --stack-name helloUserServiceStack --capabilities CAPABILITY_IAM --template-body file://<path to template file> --parameters ParameterKey=KeyPairParameter,ParameterValue=HelloUserServiceKeyPair
  
  - Wait for completion of stack creation
  
    aws cloudformation wait stack-create-complete --profile ekoksal7-eu-central-1  --stack-name helloUserServiceStack
    
  - Get laod balancer dns from stack outputs
    
    aws cloudformation describe-stacks --profile ekoksal7-eu-central-1 --stack-name helloUserServiceStack
 
 After waiting 10-15 seconds fro ec2 initialization, with load balancer dns name, you can use the API like below samples:
 
 Http Method: PUT 
 Endpoint: <Load Balancer DNS Name>/HelloUserService/hello/John
 Body:
      {
        "dateOfBirth":"1985-12-30"
      }
 Resppnse: 204 No Content
  
  
 Http Method: GET 
 Endpoint: <Load Balancer DNS Name>/HelloUserService/hello/John
 Resppnse: 200 OK
 
    a. When John's birthday is in 5 days
      
      {
        "message":"Hello, John! Your birhtday is in 5 days"
      }
    
    b. When John's birthday is today
    
      {
        "message":"Hello, John! Happy birthday!"
      }
      
  
  
  
