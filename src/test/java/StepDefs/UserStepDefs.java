package StepDefs;

import POJOs.User;
import Utils.CommonMethods;
import Utils.TestData;
import Utils.UserUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import Steps.UserSteps;
import net.serenitybdd.core.Serenity;

import java.io.IOException;

public class UserStepDefs {

    @Steps
    UserSteps userSteps;
    CommonMethods commonMethods;

    @Given("^payload for creating random user is ready$")
    public void validUserPayload() {
        TestData.createUserPayload  = userSteps.generateRandomDataForUser();
        System.out.println(TestData.createUserPayload);
    }

    @When("user sends a POST request for creating user")
    public void createUser() {
        userSteps.createUser(TestData.createUserPayload);
    }

    @And("^verify response has a user details as per random data$")
    public void verifyUserDetails(){
        userSteps.verifyUserResponse();
    }

    @Given("^payload for creating user is ready (.*)$")
    public void createUserWithJson(String fileName) {
        TestData.createUserPayload = commonMethods.mapPayloadToPojo(fileName, User.class);
        System.out.println(TestData.createUserPayload);
    }

    @And("^verify response has a user details as per json file (.*)$")
    public void verifyResponseAsPerJsonFile(String fileName) throws IOException {
        userSteps.verifyUserAsPerJson(fileName);
    }

    @When("user send get request with username as parameter")
    public void getUserByName() {
        String username = Serenity.sessionVariableCalled("createdUsername");
        userSteps.getUserDetails(username);
    }

    @When("user send get request with username as invalid username")
    public void getReqInvalidUsername() {
        userSteps.getUserDetails(UserUtil.generateRandomUsername());
    }

    @When("user sends PUT request to update user details")
    public void updateUserDetails() {
        String username = Serenity.sessionVariableCalled("createdUsername");
        userSteps.updateUserByUsername(username);
    }

    @When("user sends PUT request to update user details with invalid username")
    public void updateWithInvalidUsername() {
        userSteps.updateUserByUsername(UserUtil.generateRandomUsername());
    }

    @When("user sends a DELETE request with parameter username")
    public void deleteUserByUsername() {
        userSteps.deleteUser(Serenity.sessionVariableCalled("createdUsername"));
    }


    @When("user sends a DELETE request with invalid username")
    public void userSendsADELETERequestWithInvalidUsername() {
        userSteps.deleteUser(UserUtil.generateRandomEmail());
    }

    @When("user sends GET request to login")
    public void userSendsRequestToLogin() {
        String username =Serenity.sessionVariableCalled("createdUsername");
        String password = Serenity.sessionVariableCalled("createdPassword");
        userSteps.login(username,password);
    }

    @When("user sends GET request to login with invalid credentials")
    public void loginWithInvalidCredentials() {
//        giving random strings from util class
        userSteps.login(UserUtil.generateRandomLastName(),UserUtil.generateRandomEmail());
    }

    @When("user sends GET request to logout")
    public void userRequestToLogout() {
        String username =Serenity.sessionVariableCalled("createdUsername");
        String password = Serenity.sessionVariableCalled("createdPassword");
        userSteps.logout(username,password);
    }

    @When("user sends GET request to logout with invalid credentials")
    public void logoutWithInvalidCredentials() {
//        giving random strings from util class
        userSteps.logout(UserUtil.generateRandomLastName(),UserUtil.generateRandomEmail());
    }

    @When("user sends a POST request for creating multiple users")
    public void createMultipleUsers() {
        userSteps.createMultipleUSers(TestData.createUserPayload);
    }
}
