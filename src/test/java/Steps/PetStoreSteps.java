package Steps;

import Constants.EndPoints;
import Constants.PetStoreConstants;
import POJOs.PetStoreOrder;
import Utils.AssertionsHelpers;
import Utils.CommonMethods;
import Utils.config;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;

public class PetStoreSteps {

    CommonMethods commonMethods = new CommonMethods();

    @Step
    public void placeOrder(String payload) {
        SerenityRest.given()
                .headers(commonMethods.getDefaultHeaders())
                .body(payload)
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.PLACE_ORDER)
                .when()
                .post();
    }

    @Step
    public void verifyPlacedOrder(String fileName) throws IOException {

        PetStoreOrder petStoreOrder = commonMethods.getPojoFromJson(fileName, PetStoreOrder.class);

        Response response = SerenityRest.lastResponse();

        int orderId = response.jsonPath().getInt("id");
//        int petId = response.jsonPath().getInt("petId");
//        String status = response.jsonPath().getString("status");

        Assert.assertTrue("Order ID should be greater than 0", orderId > 0);
        AssertionsHelpers.assertResponseFieldEquals("status",petStoreOrder.getStatus());
        AssertionsHelpers.assertResponseFieldEquals("petId", petStoreOrder.getPetId());
//        Assert.assertEquals("Pet ID should be the same as the input", petId, petStoreOrder.getPetId());
//        Assert.assertEquals("Order status should be 'placed'", status, petStoreOrder.getStatus());
    }


    @Step
    public void reqGetOrderDetails(int orderId) {
        SerenityRest.given()
                .baseUri((config.getBaseUrl()))
                .pathParam("orderId", orderId)
                .when()
                .basePath(EndPoints.GET_ORDER_BY_ID)
                .get()
                .then()
                .log().body();
    }

    @Step
    public void verifyOrder(int orderId) {
        AssertionsHelpers.assertResponseFieldEquals("id", orderId);
    }

    @Step
    public void deleteOrderByID(int orderId) {
        SerenityRest
                .given()
                .baseUri(config.getBaseUrl())
                .pathParam("orderId", orderId)
                .basePath(EndPoints.DELETE_ORDER_BY_ID)
                .when()
                .delete();
    }

    @Step
    public int retrieveOrderId(){
        return SerenityRest.lastResponse().jsonPath().getInt("id");
    }
    @Step
    public void verifyOrderDeletion(int orderId) {
        String message = SerenityRest.lastResponse().jsonPath().getString("message");
        Assert.assertTrue
                ("Message does not contain expected order ID", message.contains(String.valueOf(orderId)));
    }

    @Step
    public void reqInventoryInfo() {
        SerenityRest
                .given()
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.GET_INVENTORY)
                .when()
                .get();
    }

    @Step
    public void verifyInventoryValues() {
        for (String key : PetStoreConstants.EXPECTED_STATUSES) {
            restAssuredThat(response ->
                    response.body("$", hasKey(matchesPattern("(?i)" + key)))
            );
        }
    }

    @Step
    public void reqForInvalidInventory() {
        SerenityRest
                .given()
                .baseUri(config.getBaseUrl())
                .basePath(EndPoints.GET_INVENTORY)
                .when()
                .post();
    }
}