# CloudParameters-DynamoDB
This project implements API methods to retrieve keys and values from AWS Systems manager Parameter Store and Amazon DynamoDB using the AWS Java SDK.

<h1> CST-AWSCloudParameters Project </h1> 

An extension of my CST-CloudParameters web application. This adds functionality to the web application to retrieve and filter values from a DynamoDB table, whose name is stored in AWS Systems Manager Parameter Store. 

<h2> 📄 Summary </h2> 

This project was a part of my internship for Cell Singaling Technologies (CST). At CST, I studied under CST's Director of Software Engineering. I first learned how CST leverages Amazon Web Services (AWS) to manage and build their digital infrastructure. I earned the AWS Certified Cloud Practitioner Certification and gained hands-on experience with foundational AWS services such as EC2, S3, VPCs, Systems Manager, IAM, Lambda, and more. The purpose of this project is to retrieve and filter parameters stored in Parameter Store in AWS Systems Managager. My code first securely authenticates an AWS account using an AWS access key and secret key. Note that these credentials are not hard coded into the program, as this would not be secure. Then, the program sets the AWS region and makes a request to AWS Systems Manager Parameter Store. The program implements three API functions. The first function, listParameters, lists all of the parameters in the associated AWS account's Parameter Store. The second function, listKeys, lists all of the parameters in the associated AWS account's Parameter Store that have the input prefix. The third function, getParameter, retrieves the value associated with the input parameter key in the associated AWS account's parameter store.

<h2> 💻 Softwares and Technologies </h2> 

This project (programmed in Java) uses Maven to build the code and utilizes the Spring Framework (Spring Boot). These technologies are essential to allowing the application to run. Maven converts the API specifications written in OpenAPI to source code. Spring Boot helps manage the dependencies in the application. In order to communicate with AWS, the program uses the AWS SDK for Java. 



