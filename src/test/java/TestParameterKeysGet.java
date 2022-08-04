import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestParameterKeysGet {

    public static void main(String[] args) {

        //PARAMETERS
        String parameterKey = "/azhang/dynamodb/table/1";
        String key = "Emails";

        Region awsRegion = Region.US_EAST_1;
        String tableName = "";
        String ret = "";
        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();
        keyToGet.put("Key",AttributeValue.builder().s(key).build());
        Map<String, AttributeValue> response = new HashMap<>();
        String test = "";

        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();

        try{

            GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                    .name(parameterKey)
                    .build();

            GetParameterResponse getParameterResponse = ssmClient.getParameter(getParameterRequest);
            tableName = getParameterResponse.parameter().value();

            GetItemRequest getItemRequest = GetItemRequest.builder()
                    .key(keyToGet)
                    .tableName(tableName)
                    .build();

            response = dynamoDBClient.getItem(getItemRequest).item();
            try {
                test = response.get("Value").toString();
            }
            catch (NullPointerException e) {
                System.out.println("400 error");
            }
            ret = test.substring(17, test.length()-1);
            System.out.println(response.get("Value"));
            System.out.println(ret);

        }

        catch (DynamoDbException e){
            if (tableName == null) {
                System.out.println("400 error");
               // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            else {
                System.out.println("500 error");
              //  log.error("Couldn't serialize response for content type application/json", e);
               // return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        System.out.println("500 error");
        //return null;
    }
}
