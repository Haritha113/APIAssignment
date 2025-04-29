package Steps;

import Constants.EndPoints;
import POJOs.User;
import Utils.CommonMethods;
import Utils.TestData;
import Utils.UserUtil;
import Utils.config;
import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.it.Ed;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserSteps {

    CommonMethods commonMethods;

    @Step
    public void createUser(Object createUserPayload) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .headers(commonMethods.getDefaultHeaders())
                .basePath(EndPoints.CREATE_USER)
                .body(createUserPayload)
                .when()
                .post()
                .then().log().all();

        Serenity.setSessionVariable
                ("createdUsername").to(SerenityRest.lastResponse().jsonPath().getString("[0].username"));
        Serenity.setSessionVariable
                ("createdPassword").to(SerenityRest.lastResponse().jsonPath().getString("[0].password"));
    }

    @Step
    public void verifyUserResponse() {
        Response response = SerenityRest.lastResponse();
        Assert.assertEquals(response.jsonPath().getString("[0].username"), Serenity.sessionVariableCalled("expectedUser"));
        Assert.assertEquals(response.jsonPath().getString("[0].email"), Serenity.sessionVariableCalled("expectedEmail"));
    }

    @Step
    public User generateRandomDataForUser() {
        User user = new User();
        user.setId(UserUtil.generateRandomId());
        user.setUsername(UserUtil.generateRandomUsername());
        user.setFirstName(UserUtil.generateRandomFirstName());
        user.setLastName(UserUtil.generateRandomLastName());
        user.setEmail(UserUtil.generateRandomEmail());
        user.setPassword(UserUtil.generateRandomPassword());
        user.setPhone(UserUtil.generateRandomPhone());
        user.setUserStatus(UserUtil.generateRandomUserStatus());

        //for validation purpose
        Serenity.setSessionVariable("expectedUser").to(user.getUsername());
        Serenity.setSessionVariable("expectedEmail").to(user.getEmail());

        return user;

    }


    @Step
    public void verifyUserAsPerJson(String fileName) throws IOException {

        List<User> users = commonMethods.getPojoFromJson(fileName, new TypeReference<List<User>>() {});

        List<String> expectedUsernames = users.stream()
                .map(User::getUsername)
                .toList();

        List<String> actualUsernames = Serenity.sessionVariableCalled("createdUsernames");

        Assert.assertTrue(expectedUsernames.containsAll(actualUsernames));

    }

    @Step
    public void getUserDetails(String username) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .headers(commonMethods.getDefaultHeaders())
                .param("username",username)
                .basePath(EndPoints.GET_USER_BY_USERNAME)
                .when()
                .get()
                .then()
                .log().all();
    }

    @Step
    public void updateUserByUsername(String username) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .header("Cookie", TestData.sessionToken)
                .param("username",username)
                .basePath(EndPoints.UPDATE_USER)
                .when()
                .put()
                .then()
                .log().all();
        Serenity.setSessionVariable
                ("createdUsername").to(SerenityRest.lastResponse().jsonPath().getString("[0].username"));
        Serenity.setSessionVariable
                ("createdPassword").to(SerenityRest.lastResponse().jsonPath().getString("[0].password"));
    }

    @Step
    public void deleteUser(String username) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .header("Cookie", TestData.sessionToken)
                .param("username",username)
                .basePath(EndPoints.DELETE_USER)
                .when()
                .delete()
                .then()
                .log().all();
    }

    @Step
    public void login(String username, String password) {
        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .queryParam("username", username)
                .queryParam("password", password)
                .basePath(EndPoints.LOGIN_USER)
                .when()
                .get()
                .then()
                .log().all();

        /* swagger has no doc on which type of header it is and it is not used anywhere for other api calls
          so just retrieving from response message for code flow purpose
         "message": "logged in user session:1745945187385"
        TestData.sessionToken = response.getHeader("Set-Cookie");
         */
        String message = SerenityRest.lastResponse().jsonPath().getString("message");
        String sessionId = message.split(":")[1];
    }

    @Step
    public void logout(String username, String password) {
        Response response = (Response) SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .queryParam("username", username)
                .queryParam("password", password)
                .basePath(EndPoints.LOGOUT_USER)
                .when()
                .get()
                .then()
                .log().all();
    }

    @Step
    public void createMultipleUSers(Object createUserPayload) {

        SerenityRest.given()
                .baseUri(config.getBaseUrl())
                .headers(commonMethods.getDefaultHeaders())
                .basePath(EndPoints.CREATE_USERS_WITH_ARRAY)
                .when()
                .post()
                .then().log().all();
        List<String> usernames = SerenityRest.lastResponse().jsonPath().getList("username");
        Serenity.setSessionVariable("createdUsernames").to(usernames);
    }
}
