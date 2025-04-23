package StepDefs;

import Steps.CreatePetSteps;
import Utils.EndPoints;
import Utils.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreatePetStepDefs {

    CreatePetSteps createPetSteps = new CreatePetSteps();

    @Given("^payload for creating pet is ready (.*)$")
    public void validPetPayload(String fileName) {
        TestData.createPetJson  = createPetSteps.createPetPayloadUsingPojo(fileName);
        System.out.println(TestData.createPetJson);
    }


    @When("user sends a POST request")
    public void createPetPostRequest() {
        createPetSteps.createPetRequest(TestData.createPetJson, EndPoints.CREATE_PET);
    }

    @Then("response should be successful with status code {int}")
    public void responseShouldBeSuccessful(int statusCode) {
        createPetSteps.verifyResponseCode(statusCode);
    }


    @And("verify response has a pet details")
    public void verifyResponse() {
        createPetSteps.verifyResponse();
    }
}
