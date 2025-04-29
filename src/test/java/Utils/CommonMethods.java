package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonMethods {

    // Method to read JSON from file into POJO
    public <T> T getPojoFromJson(String fileName,Class<T> classType) throws IOException {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the JSON file into the Pet object

        return objectMapper.readValue(new File("src/test/resources/payloads/" + fileName), classType);
    }

    public <T> T fromJson(String jsonString, Class<T> classType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, classType);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON string to POJO: " + e.getMessage(), e);
        }
    }


    public void postRequest(String payload,String basePath) {

//        SerenityRest.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

       SerenityRest.given()
                .log().all()
                .baseUri(config.getBaseUrl())
                .basePath(basePath)
                .headers(getDefaultHeaders())
                .body(payload)
                .when()
                .post();

    }

    public Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    public Map<String, String> getHeadersWithAuth(String token) {
        Map<String, String> headers = getDefaultHeaders();
        headers.put("Authorization", "Bearer " + token);
        return headers;
    }

    public void sendPostwithBasicAuth(String username,String password,String basePath){
        SerenityRest.given()
                .auth().basic("username", "password")
                .basePath(basePath)
                .headers(getDefaultHeaders())
                .when()
                .post();
    }



}
