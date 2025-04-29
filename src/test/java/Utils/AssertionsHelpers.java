package Utils;
import static org.hamcrest.Matchers.equalTo;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;

public class AssertionsHelpers {

    public static void assertNameEquals(String expectedName) {
        restAssuredThat(response -> response.body("name", equalTo(expectedName)));
    }

    public static void assertStatusEquals(String expectedStatus) {
        restAssuredThat(response -> response.body("status", equalTo(expectedStatus)));
    }

    public static void assertPetIdEquals(Long expectedId) {
        restAssuredThat(response -> response.body("id", equalTo(expectedId.intValue())));
    }

    public static void assertResponseStatusCode(int statusCode) {
        restAssuredThat(response -> response.statusCode(statusCode));
    }

}
