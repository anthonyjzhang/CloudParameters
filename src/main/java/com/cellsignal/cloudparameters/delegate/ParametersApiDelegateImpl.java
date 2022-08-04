package com.cellsignal.cloudparameters.delegate;

import com.cellsignal.cloudparameters.api.ParametersApiDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class ParametersApiDelegateImpl implements ParametersApiDelegate {
    @Override
    public ResponseEntity<List<String>> parametersGet(String prefix) {
        Region awsRegion = Region.US_EAST_1;
        SsmClient ssmClient = SsmClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("default"))
                .region(awsRegion)
                .build();
        List<String> ret = new ArrayList<>();
        String convert = prefix.replace("%2F", "/");


        try {
            GetParametersByPathRequest getParametersRequest = GetParametersByPathRequest
                    .builder()
                    .path(convert)
                    .recursive(true)
                    .build();

            GetParametersByPathResponse getParametersResponse = ssmClient.getParametersByPath(getParametersRequest);
            List<Parameter> params = getParametersResponse.parameters();
            Iterator<Parameter> parameterIterator = params.iterator();

            while (parameterIterator.hasNext()) {
                Parameter parameter = parameterIterator.next();
                System.out.println(parameter.name());
                ret.add(parameter.name());
            }

        }
        catch (SsmException e) {
            if (ret.isEmpty()){
                System.out.println("400 error");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            System.out.println("500 error");
            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (ret.isEmpty()){
            System.out.println("400 error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        System.out.println(ret);
        return ResponseEntity.ok(ret);
    }
}