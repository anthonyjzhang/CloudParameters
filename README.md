# ☁️ AWS Cloud Parameters 

The purpose of this project is to retrieve and filter parameters stored in Parameter Store in AWS Systems Managager. The program securely authenticates an AWS account using an AWS access key and secret key. Then, the program sets the AWS Region and makes a request to AWS Systems Manager Parameter Store. The program implements three API functions. The first function, listParameters, lists all parameters in the associated AWS account's Parameter Store. The second function, listKeys, lists all of the parameters in the associated AWS account's Parameter Store that contain an input prefix, allowing you to filter parameters by environment, owner, and other identifiers. The third function, getParameter, allows you to retrieve the value of the parameter associated with an input key in the associated AWS account's Parameter Store. Each function returns HTTP status responses. 

Then, integration with Amazon DynamoDB Tables was added to the web application. This function, parameterKeysGet, takes two parameters: a key to a parameter in Parameter Store, and a key to a value in an Amazon DynamoDB Table. The value of the input key in Parameter Store holds the name of a valid DynamoDB Table in the same AWS account. The second parameter should be a valid key in the same DynamoDB Table. After authenticating the AWS account and setting an AWS Region, the program uses a SSm Client to make a request to Amazon Systems Manager Parameter Store to retrieve the value associated with the input parameter key. The program stores the reponse, the table name of a valid DynamoDB Table, and then uses this value to make a request to DynamoDB. Using a DynamoDB client, the program requests to retrieve the item associated with the input table key in the stored DynamoDB table name. The program receives the response, filters out unecessary metadata, then returns the item. 

<h2> 💻 Softwares and Technologies </h2> 

<div align="center">
  <a href = "https://www.credly.com/earner/earned/badge/6bb63137-7db5-4f17-838b-b2f95a1d874d" target = "_blank"><img src="https://images.credly.com/size/680x680/images/0e284c3f-5164-4b21-8660-0d84737941bc/image.png" width = "160" height = "160"/></a>
  <a href = "https://www.credly.com/earner/earned/badge/1b9d0fcf-a738-4730-9bd6-9dd9abc86af5" target = "_blank" ><img src="https://images.credly.com/size/680x680/images/00634f82-b07f-4bbd-a6bb-53de397fc3a6/image.png" width = "160" height = "160"/></a>
</div>
        <p>

  
</p>                           

<div align="center">
  <img src="https://img.shields.io/badge/Amazon%20DynamoDB-4053D6?style=for-the-badge&logo=Amazon%20DynamoDB&logoColor=white"/>


  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white"/>
   <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
 <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
 <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white"/>
 <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot"/>
  <img src="https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white"/>
  

</div>
 





