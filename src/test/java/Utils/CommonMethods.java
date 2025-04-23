package Utils;

import POJOs.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.io.File;
import java.io.IOException;

public class CommonMethods {

    // Method to read JSON from file into POJO
    public <T> T readFromJsonFileAndMapToPojo(String fileName,Class<T> classType) throws IOException {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the JSON file into the Pet object

        return objectMapper.readValue(new File("src/test/resources/payloads/" + fileName), classType);
    }


    public void postRequest(String payload,String basePath) {

        System.out.println("Entered POST");
        Response response = SerenityRest.given()
                .log().all()
                .baseUri(config.getBaseUrl())
                .basePath(basePath)
                .contentType("application/json")
                .body(payload)
                .when()
                .post();
    }
}
