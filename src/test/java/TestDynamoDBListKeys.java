import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDynamoDBListKeys {

    public static void main(String[] args) {
        Region awsRegion = Region.US_EAST_1;
        String tableName = "";
        String parameterKey = "/azhang/dynamodb/table/1";
        List<String> ret = new ArrayList<>();

        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                .name(parameterKey)
                .build();
        GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);
        tableName = getParameterResponse.parameter().value();

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
}