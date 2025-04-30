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
import java.util.List;


public class PetSteps {

    CommonMethods commonMethods = new CommonMethods();

    @Step
    public void createPetRequest(String payload){
        commonMethods.postRequest(payload,EndPoints.CREATE_PET);
    }

    @Step
    public int retrievePetIdAfterCreating(){
        return TestData.petId = SerenityRest.lastResponse().jsonPath().getInt("id");
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

        Object responseField = SerenityRest.lastResponse().jsonPath().get();

        if (responseField instanceof String) {
            // Single object response
            AssertionsHelpers.assertNameEquals(expectedName);
            AssertionsHelpers.assertStatusEquals(petStatus);
            Assert.assertEquals(expectedCategoryName, SerenityRest.lastResponse().jsonPath().getString("category.name"));

        } else if (responseField instanceof List) {
            // List of values, check if one of the values matches
            List<String> statusList = SerenityRest.lastResponse().jsonPath().getList("status");
            Assert.assertTrue(statusList.contains(petStatus));
            List<String> names = SerenityRest.lastResponse().jsonPath().getList("name");
            Assert.assertTrue("Expected name not found in response list",
                    names.contains(expectedName));
            }
        }

    @Step
    public String getAccessToken(String userName, String password) {
        //sent post request with credentials and fetch token from response
        return TestData.accessToken;
    }

    @Step
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

    @Step
    public void getPetReqById(int petId) {

        if(!String.valueOf(petId).isEmpty()) {
            SerenityRest.
                    given()
                    .log().all()
                    .baseUri(config.getBaseUrl())
                    .pathParam("petId", petId)
                    .basePath(EndPoints.GET_PET_BY_ID)
                    .when()
                    .get().then().log().all();
        }
        else {

            System.out.println("expected after deletion");
        }
    }

    @Step
    public void verifyPetIdInGetPetIdReq(int petId){
        AssertionsHelpers.assertPetIdEquals(petId);
    }

    @Step
    public void updatePetReq(String createPetPayload) {
                SerenityRest.
                        given()
                        .log().all()
                        .baseUri(config.getBaseUrl())
                        .basePath(EndPoints.UPDATE_PET)
                        .headers(commonMethods.getDefaultHeaders())
                        .and()
                        .body(createPetPayload)
                        .when()
                        .put();
    }

    @Step
    public void updatePetNameAndStatus(String randomPetName,String status,int petId) {

        Pet pet = new Pet();
        pet.setId(petId);
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
                .post();

//        TestData.petId =SerenityRest.lastResponse().jsonPath().getInt("id");
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
    public void updatePetImage(String fileName,int petId) {

        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.UPDATE_PET_PHOTO)
                .pathParam("petId",petId)
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
    public void deleteReq(int petId) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .header("api_key", "special-key") //optional,so giving dummy
                .pathParam("petId", petId)
                .when()
                .delete(EndPoints.DELETE_PET_BY_ID);
    }

    @Step
    public void verifyDeletionOfPet(int petId) {
        Assert.assertEquals(SerenityRest.lastResponse().jsonPath().get("message"),(String.valueOf(TestData.petId)));
    }

    public void verifyEmptyResponse() {
        List<Object> pets = SerenityRest.lastResponse().jsonPath().getList("$");
        Assert.assertTrue("Expected empty list for invalid status", pets.isEmpty());
    }

    public void updatePetImageWithInvalidUrl(String imagePath, int petId) {
        SerenityRest.given()
                .basePath(EndPoints.UPDATE_PET_PHOTO)
                .pathParam("petId",petId)
                .multiPart("file", new File(imagePath)) // form-data: file
                .when()
                .post();
    }
}
