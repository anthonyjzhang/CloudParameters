# ☁️ AWS Cloud Parameters 

<h2> 📄 Summary </h2> 

The purpose of this project is to retrieve and filter parameters stored in Parameter Store in AWS Systems Managager. The program securely authenticates an AWS account using an AWS access key and secret key. Then, the program sets the AWS Region and makes a request to AWS Systems Manager Parameter Store. The program implements three API functions. The first function, listParameters, lists all parameters in the associated AWS account's Parameter Store. The second function, listKeys, lists all of the parameters in the associated AWS account's Parameter Store that contain an input prefix, allowing you to filter parameters by environment, owner, and other identifiers. The third function, getParameter, allows you to retrieve the value of the parameter associated with an input key in the associated AWS account's Parameter Store. Each function returns HTTP status responses. 

Then, integration with Amazon DynamoDB Tables was added to the web application. This function, parameterKeysGet, takes two parameters: a key to a parameter in Parameter Store, and a key to a value in an Amazon DynamoDB Table. The value of the input key in Parameter Store holds the name of a valid DynamoDB Table in the same AWS account. The second parameter should be a valid key in the same DynamoDB Table. After authenticating the AWS account and setting an AWS Region, the program uses a SSm Client to make a request to Amazon Systems Manager Parameter Store to retrieve the value associated with the input parameter key. The program stores the reponse, the table name of a valid DynamoDB Table, and then uses this value to make a request to DynamoDB. Using a DynamoDB client, the program requests to retrieve the item associated with the input table key in the stored DynamoDB table name. The program receives the response, filters out unecessary metadata, then returns the item. 

<h2> 💻 Softwares and Technologies </h2> 

- Amazon Web Services (AWS)
- AWS SDK for Java
- Maven
- Spring Boot




