package com.cellsignal.cloudparameters.delegate;

import com.cellsignal.cloudparameters.api.ParameterApiDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


import java.util.*;

@Service
public class ParameterApiDelegateImpl implements ParameterApiDelegate {

    @Override
    public ResponseEntity<String> parameterGet(String parameterKey) {
        Region awsRegion = Region.US_EAST_1;
        String ret;
        String checkValue = null;
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();

        try {
            GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                    .name(parameterKey)
                    .build();
            GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);
            checkValue = getParameterResponse.parameter().value();
            ret = "The parameter value is: " + getParameterResponse.parameter().value();
            System.out.println(ret);


        } catch (SsmException e) {
            if (checkValue == null) {
                System.out.println("400 error");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            System.out.println("500 error");
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<List<String>> parameterKeysGet(String parameterKey) {

        Region awsRegion = Region.US_EAST_1;
        String tableName = null;
        List<String> ret = new ArrayList<>();

        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();

        try {
            try {
                GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                        .name(parameterKey)
                        .build();
                GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);
                tableName = getParameterResponse.parameter().value();
            }
            catch (SsmException e) {
                System.out.println("400 error");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
                ScanRequest scanRequest = ScanRequest.builder()
                        .tableName(tableName)
                        .build();

                ScanResponse result = dynamoDBClient.scan(scanRequest);

            for (Map<String, AttributeValue> item : result.items()) {
                String convert = item.get("Key").s();
                ret.add(convert);
                System.out.println(convert);
            }
            System.out.println(ret);

        }

        catch (DynamoDbException e){
                System.out.println("500 error");
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ret);
    }


    @Override
    public ResponseEntity<String> parameterKeyGet(String parameterKey, String key) {

        Region awsRegion = Region.US_EAST_1;
        String tableName = null;
        String ret = null;
        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();
        keyToGet.put("Key",AttributeValue.builder().s(key).build());
        Map<String, AttributeValue> response = new HashMap<>();

        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();

        try{

            try {
                GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                        .name(parameterKey)
                        .build();
                GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);
                tableName = getParameterResponse.parameter().value();
            }
            catch (SsmException e) {
                if (tableName == null) {
                    System.out.println("400 error");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            GetItemRequest getItemRequest = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();

            response = dynamoDBClient.getItem(getItemRequest).item();

            try {
                ret = response.get("Value").s();
            }
            catch (NullPointerException e) {
                System.out.println("400 error");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            System.out.println(response.get("Value"));
            System.out.println(ret);

        }

        catch (DynamoDbException e) {
                System.out.println("500 error");
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return ResponseEntity.ok(ret);
    }
}

