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
import javax.inject.Inject;
import net.serenitybdd.rest.SerenityRest;

import java.io.IOException;

public class PetStepDefs {


//    @Inject
//    CommonMethods commonMethods;

    @Steps
    PetSteps petSteps;

    CommonMethods commonMethods = new CommonMethods();

    @Given("^payload for creating pet is ready (.*)$")
    public void validPetPayload(String fileName) throws IOException {
            TestData.createPetPayload = commonMethods.createPayloadByFileRead(fileName);
    }


    @When("user sends a POST request")
    public void createPetPostRequest() {
        petSteps.createPetRequest(TestData.createPetPayload);
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
    }


    @When("^verify get pet data$")
    public void verifyPetId() {
        petSteps.verifyPetIdInGetPetIdReq(petSteps.retrievePetIdAfterCreating());
    }

    @When("^user sends a GET request for pet with invalid id$")
    public void reqPetByInvalidPetId() {
        petSteps.getPetReqById(PetConstants.invalidId);
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
        petSteps.updatePetImage(imagePath, petSteps.retrievePetIdAfterCreating());
    }

    @When("^user sends a invalid PUT request to update pet with image (.*)$")
    public void updatePetWithInvalidUrl(String imagePath) {
        petSteps.updatePetImageWithInvalidUrl(imagePath, petSteps.retrievePetIdAfterCreating());
    }

    @When("user sends a Delete request for pet")
    public void userSendsADeleteRequestForPet() {
        petSteps.deleteReq(petSteps.retrievePetIdAfterCreating());
    }

    @Then("verify pet deletion")
    public void verifyPetDeletion() {
        petSteps.verifyDeletionOfPet(TestData.petId);
    }

    @When("^user sends a GET request for pet by status (.*)$")
    public void getPetByStatus(String status) {
        petSteps.getPetReqByStatus(String.valueOf(status));
    }

    @When("user sends a PUT request to update pet name and status with no petid")
    public void updatePetWithNoPetid() {
        String status = PetConstants.SOLD;
        petSteps.updatePetNameAndStatus(PetUtil.generateRandomPetName(),status,PetUtil.generateRandomId());
    }

    @Then("verify that response is empty")
    public void verifyThatResponseIsEmpty() {
        petSteps.verifyEmptyResponse();
    }
}
