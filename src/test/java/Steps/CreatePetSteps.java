package Steps;

import POJOs.Pet;
import Utils.CommonMethods;
import Utils.EndPoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreatePetSteps {


    CommonMethods commonMethods = new CommonMethods();


    @Step
    public String createPetByFileRead(String fileName) throws IOException {
        return Files.readString(Paths.get("src/test/resources/Payloads/"+ fileName));

    }

    @Step
    public void createPetRequest(String payload,String endPoint){
        commonMethods.postRequest(payload,EndPoints.CREATE_PET);
    }

    // Method to send the Pet object as JSON in request
    @Step
    public String createPetPayloadUsingPojo(String fileName) {
        try {
            Pet pet = commonMethods.readFromJsonFileAndMapToPojo(fileName, Pet.class);
            return new ObjectMapper().writeValueAsString(pet);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Step
    public void verifyResponseCode(int statusCode) {
        SerenityRest.restAssuredThat(response -> response.statusCode(statusCode));
    }

    @Step
    public void verifyResponse() {
        Response response = SerenityRest.lastResponse();
        System.out.println(response.body().asPrettyString());
    }
}
