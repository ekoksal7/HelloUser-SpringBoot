# HelloUser API Spring Boot AWS Solution

HelloUser API is developed by Spring Boot framework. This API runs on tomcat web container on EC2 instances. EC2 instances are created from a AMI that has java and tomcat in it. When ec2 instances are started up, it gets the application from github and deploy it to the tomcat by running the below script.

#!/bin/bash
cd /home/ec2-user/projects/
rm -rf HelloUser-SpringBoot
git clone https://github.com/ekoksal7/HelloUser-SpringBoot.git
cp -f HelloUser-SpringBoot/HelloUserService.war /home/ec2-user/apache-tomcat-9.0.8/webapps/
cd /home/ec2-user/apache-tomcat-9.0.8/bin/
./startup.sh

With the cloud formation template file <teamplate_file_name> in this repository, you can create the aws solution that were shown on <diagram name> by running below script with aws cli.
  
 You should run the script for eu-central-1 region. Also before running it, you should create a key pair for ec2 instances and give it as a parameter to the script.
 
 When script completes creation of template, it gives the DNS name of the load balancer as output. With this DNS name, you can use the API like below samples:
 
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
      
  
  
  
