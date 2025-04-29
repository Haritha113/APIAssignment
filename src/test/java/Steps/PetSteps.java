package Steps;

import POJOs.Pet;
import Utils.AssertionsHelpers;
import Utils.CommonMethods;
import Constants.EndPoints;
import Utils.TestData;
import Utils.config;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PetSteps {


    CommonMethods commonMethods;


    @Step
    public String createPetPayloadByFileRead(String fileName) throws IOException {
        return Files.readString(Paths.get("src/test/resources/Payloads/"+ fileName));

    }

    @Step
    public void createPetRequest(String payload){
        commonMethods.postRequest(payload,EndPoints.CREATE_PET);
        TestData.petId =SerenityRest.lastResponse().jsonPath().getLong("id");
    }

    // Method to send the Pet object as JSON in request
    @Step
    public Pet createPetPayloadUsingPojo(String fileName) {
        try {
            return commonMethods.getPojoFromJson(fileName, Pet.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Step
    public void verifyResponseCode(int statusCode) {
        AssertionsHelpers.assertResponseStatusCode(statusCode);
    }

    @Step
    public void verifyResponseAsPerJson(String fileName) throws IOException {

        Pet petRequest = commonMethods.getPojoFromJson(fileName,Pet.class);

        String expectedName = petRequest.getName();
        String expectedCategoryName = (petRequest.getCategory() != null) ? petRequest.getCategory().getName() : null;
        String petStatus = petRequest.getStatus();

        AssertionsHelpers.assertNameEquals(expectedName);
        AssertionsHelpers.assertStatusEquals(petStatus);
        Assert.assertEquals(expectedCategoryName, SerenityRest.lastResponse().jsonPath().getString("category.name"));

    }

    @Step
    public String getAccessToken(String userName, String password) {
        //sent post request with credentials and fetch token from response
        return TestData.accessToken;
    }

    public void createPetWithAuth(String accessToken) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.CREATE_PET)
                .headers(commonMethods.getHeadersWithAuth(accessToken))
                .and()
                .body(TestData.createPetPayload)
                .when()
                .post();
        // TODO - validation fr headers
         }


    public void getPetReqById() {
        SerenityRest.
                given()
                .log().all()
                .baseUri(config.getBaseUrl())
                .pathParam("petId",TestData.petId)
                .basePath(EndPoints.GET_PET_BY_ID)
                .when()
                .get();
        AssertionsHelpers.assertPetIdEquals(TestData.petId);
    }

    public void updatePetReq(String createPetPayload) {
                SerenityRest.
                        given()
                        .log().all()
                        .baseUri(config.getBaseUrl())
                        .basePath(EndPoints.UPDATE_PET)
                        .and()
                        .body(createPetPayload)
                        .when()
                        .put();
        TestData.petId =SerenityRest.lastResponse().jsonPath().getLong("id");
    }

    @Step
    public void updatePetNameAndStatus(String randomPetName,String status) {

        Pet pet = new Pet();
        pet.setId((int) TestData.petId);
        pet.setName(randomPetName);
        pet.setStatus(status);

        SerenityRest.
                given()
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.UPDATE_PET_WITH_FORM_DATA)
                .and()
                .headers(commonMethods.getDefaultHeaders())
                .body(pet)
                .when()
                .post().then().log().all();

        TestData.petId =SerenityRest.lastResponse().jsonPath().getLong("id");
//        Serenity.setSessionVariable("petId").to(SerenityRest.lastResponse().jsonPath().getLong("id"));
        Serenity.setSessionVariable("petName").to(randomPetName);
        Serenity.setSessionVariable("petStatus").to(status);
    }

    @Step
    public void verifyPetNameAndStatus() {
        Response response = SerenityRest.lastResponse();

//        String actualName =  response.jsonPath().getString("name");
//        String actualStatus = response.jsonPath().getString("status");

        String petName = Serenity.sessionVariableCalled("petName");
        String petStatus = Serenity.sessionVariableCalled("petStatus");

        AssertionsHelpers.assertNameEquals(petName);
        AssertionsHelpers.assertStatusEquals(petStatus);
    }

    @Step
    public void updatePetImage(String fileName) {
        SerenityRest.given()
                .basePath(EndPoints.UPDATE_PET_PHOTO)
                .pathParam("petId",TestData.petId)
                .multiPart("file", new File(fileName)) // form-data: file
                .when()
                .post()
                .then()
                .log().all();
    }

    @Step
    public void verifyPetImage() {
        Assert.assertTrue(SerenityRest.lastResponse().body().asString().contains("uploaded"));

    }

    @Step
    public void getPetReqByStatus(String status) {
                SerenityRest.
                        given()
                        .queryParam("status", status) // TODO - we can get from json and give status based on that is another approach
                        .baseUri(config.getBaseUrl())
                        .basePath(EndPoints.FIND_BY_STATUS)
                        .when()
                        .get()
                        .then()
                        .log().all();
    }

    @Step
    public void deleteReq(long petId) {
        SerenityRest.given()
                .header("api_key", "value") //optional,so giving dummy
                .pathParam("petId", petId)
                .when()
                .delete(EndPoints.DELETE_PET_BY_ID)
                .then()
                .log().all();
    }

    @Step
    public void verifyDeletionOfPet(long petId) {
        Assert.assertEquals(SerenityRest.lastResponse().jsonPath().get("message"),(String.valueOf(TestData.petId)));
    }
}
