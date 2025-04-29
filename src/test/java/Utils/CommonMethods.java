package Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CommonMethods {

    // Method to read JSON from file into POJO
    public <T> T getPojoFromJson(String fileName,Class<T> classType) throws IOException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            // Read the JSON file into the Pojo object
            return objectMapper.readValue(new File("src/test/resources/Payloads/" + fileName), classType);
        }
        catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON string to POJO: " + e.getMessage(), e);
        }
    }



    public <T> T mapPayloadToPojo(String fileName, Class<T> classType) {
        try {
            return getPojoFromJson(fileName, classType);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public <T> T getPojoFromJson(String fileName, TypeReference<T> typeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(fileName), typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + fileName, e);
        }
    }


    public void postRequest(String payload,String basePath) {
       SerenityRest.given()
                .log().all()
                .baseUri(config.getBaseUrl())
                .basePath(basePath)
                .headers(getDefaultHeaders())
                .body(payload)
                .when()
                .post();
    }

    @Step
    public Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    @Step
    public String createPayloadByFileRead(String fileName) throws IOException {
        return Files.readString(Paths.get("src/test/resources/Payloads/"+ fileName));

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
