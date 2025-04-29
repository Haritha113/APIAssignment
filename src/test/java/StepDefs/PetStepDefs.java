package StepDefs;

import Constants.PetConstants;
import Steps.PetSteps;
import Utils.CommonMethods;
import Utils.PetUtil;
import Utils.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.rest.SerenityRest;

import java.io.IOException;

public class PetStepDefs {

    @Steps
    PetSteps petSteps;
    CommonMethods commonMethods;

    @Given("^payload for creating pet is ready (.*)$")
    public void validPetPayload(String fileName) throws IOException {
//        String[] eachFile = fileName.split(",");
//        for(String file:eachFile) {
            TestData.createPetPayload = commonMethods.createPayloadByFileRead(fileName);
//        }
        System.out.println(TestData.createPetPayload);
    }


    @When("user sends a POST request")
    public void createPetPostRequest() {
        petSteps.createPetRequest(TestData.createPetPayload);
        TestData.petId = SerenityRest.lastResponse().jsonPath().getInt("id");
    }

    @Then("response should be successful with status code {int}")
    public void responseShouldBeSuccessful(int statusCode) {
        petSteps.verifyResponseCode(statusCode);
    }


    @And("^verify response has a pet details as per json file (.*)$")
    public void verifyResponseAsPerJson(String fileName) throws IOException {
        petSteps.verifyResponseAsPerJson(fileName);
    }

    @Given("^get the access token for authorization (.*) (.*)$")
    public void getAccessToken(String userName,String password) {
        petSteps.getAccessToken(userName,password);
    }

    @When("user sends a POST request with Authorization")
    public void requestPOSTWithAuthorization() {
        petSteps.createPetWithAuth(TestData.accessToken);
    }

    @When("^user sends a GET request for pet$")
    public void reqPetByPetId() {
            petSteps.getPetReqById(TestData.petId);
            petSteps.verifyPetIdInGetPetIdReq(TestData.petId);
    }

    @When("^user sends a GET request for pet with invalid id$")
    public void reqPetByInvalidPetId() {
        petSteps.getPetReqById(Integer.parseInt(PetUtil.generateRandomPetName()));
    }

    @When("^user sends a PUT request with updated pet data (.*)$")
    public void userSendsAPUTReq(String fileName) throws IOException {
        TestData.createPetPayload = commonMethods.createPayloadByFileRead(fileName);
        petSteps.updatePetReq(TestData.createPetPayload);
    }

    @Then("^verify updated pet details with petName and status$")
    public void verifyUpdatedPetNameAndStatus() {
        petSteps.verifyPetNameAndStatus();
    }

    @When("user sends a PUT request to update pet name and status")
    public void userSendsAPUTRequestToUpdatePetNameAndStatus() {
            String randomPetName = PetUtil.generateRandomPetName();
            String status = PetConstants.AVAILABLE;
            petSteps.updatePetNameAndStatus(randomPetName,status,TestData.petId);
    }

    @Then("verify updated pet image$")
    public void verifyUpdatedPetImage() {
        petSteps.verifyPetImage();
    }

    @When("^user sends a PUT request to update pet with image (.*)$")
    public void updatePetImage(String imagePath) {
        petSteps.updatePetImage(imagePath);
    }

    @When("user sends a Delete request for pet")
    public void userSendsADeleteRequestForPet() {
        petSteps.deleteReq(TestData.petId);
    }

    @Then("verify pet deletion")
    public void verifyPetDeletion() {
        petSteps.verifyDeletionOfPet(TestData.petId);
    }

    @When("^user sends a GET request for pet by status (.*)$")
    public void getPetByStatus(String status) {
        petSteps.getPetReqByStatus(status);
    }

    @When("user sends a PUT request to update pet name and status with no petid")
    public void updatePetWithNoPetid() {
        String randomPetName = PetUtil.generateRandomPetName();
        String status = PetConstants.SOLD;
        petSteps.updatePetNameAndStatus(randomPetName,status,Integer.valueOf(PetUtil.generateRandomPetName()));
    }
}
