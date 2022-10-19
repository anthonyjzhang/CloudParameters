# ☁️ AWSCloudParametersWithDynamoDB Project
This is an extension of my AWSCloudParameters web application. This adds functionality to retrieve and filter values from a DynamoDB table, whose name is stored in AWS Systems Manager Parameter Store. 

<h2> 📄 Summary </h2> 

This project implements all of the functions in my AWSCloudParameters project. However, this project adds an additional function that adds integration with Amazon DynamoDB Tables to the web application. This function, parameterKeysGet, takes two parameters: a key to a parameter in Parameter Store, and a key to a value in an Amazon DynamoDB Table. The value of the input key in Parameter Store should hold the name of a valid DynamoDB Table in the same AWS account. The second parameter should be a valid key in the same DynamoDB Table. After authenticating the AWS account and setting an AWS Region, the program uses a SSm Client to make a request to Amazon Systems Manager Parameter Store to retrieve the value associated with the input parameter key. The program stores the reponse, which should be the table name of a valid DynamoDB Table, and then uses this value to make a request to DynamoDB. Using a DynamoDB client, the program requests to retrieve the item associated with the input table key in the stored DynamoDB table name. The program receives the response, filters out unecessary metadata, then returns the item. Like the previous version of the application, each function returns HTTP status responses. Status code 100 for a valid request, status code 400 for a bad request due to an invalid input, and status code 500 for an internal server error. 

<h2> 💻 Softwares and Technologies </h2> 

- Amazon Web Services (AWS)
- AWS SDK for Java
- Maven
- Spring Boot




