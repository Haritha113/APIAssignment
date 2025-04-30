package StepDefs;

import Constants.PetStoreConstants;
import Utils.CommonMethods;
import Utils.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import Steps.PetStoreSteps;
import net.serenitybdd.core.Serenity;

import java.io.IOException;

public class PetStoreStepDefs {


    @Steps
    PetStoreSteps petStoreSteps;
    CommonMethods commonMethods = new CommonMethods();

    @Given("^payload for purchasing pet is ready (.*)$")
    public void validPetStorePayload(String fileName) throws IOException {
        TestData.createPetStorePayload = commonMethods.createPayloadByFileRead(fileName);
        System.out.println(TestData.createPetStorePayload);
    }


    @And("^verify response has a order details as per json file (.*)$")
    public void verifyOrderDetails(String fileName) throws IOException {
        petStoreSteps.verifyPlacedOrder(fileName);
    }

    @When("user sends a POST request for placing an order")
    public void reaToPlaceOrder() {
        petStoreSteps.placeOrder(TestData.createPetStorePayload);
    }

    @When("user sends a GET request to get order details")
    public void getOrderDetails() {
        TestData.petStoreId = petStoreSteps.retrieveOrderId();
        petStoreSteps.reqGetOrderDetails(TestData.petStoreId);
    }

    @Then("verify order info")
    public void verifyOrderInfo() {
        TestData.petStoreId = petStoreSteps.retrieveOrderId();
        petStoreSteps.verifyOrder(TestData.petStoreId);
    }

    @When("user sends a GET request with invalid order id")
    public void userSendsInvalidOrderId() {
        petStoreSteps.reqGetOrderDetails(PetStoreConstants.INVALID_ORDER_ID);
    }

    @When("user sends a DELETE request to delete order details")
    public void deleteOrderDetails() {
        TestData.petStoreId = petStoreSteps.retrieveOrderId();
        petStoreSteps.deleteOrderByID(TestData.petStoreId);
    }

    @Then("verify message contains deleted order ID")
    public void verifyOrderDeletion() {
        petStoreSteps.verifyOrderDeletion(TestData.petStoreId);
    }

    @When("^user sends a DELETE request to delete order details with invalid id$")
    public void deleteInvalidId() {
        petStoreSteps.deleteOrderByID(PetStoreConstants.INVALID_ORDER_ID);
    }

    @When("user sends a GET inventory request")
    public void getInventoryRequest() {
        petStoreSteps.reqInventoryInfo();
    }

    @Then("inventory map should contain expected keys")
    public void verifyInventoryData() {
        petStoreSteps.verifyInventoryValues();
    }

    @When("user sends a GET inventory request with invalid method type")
    public void checkInvalidInventory() {
        petStoreSteps.reqForInvalidInventory();
    }
}
