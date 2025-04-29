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

    public static void assertPetIdEquals(int expectedId) {
        restAssuredThat(response -> response.body("id", equalTo(expectedId)));
    }

    public static void assertResponseStatusCode(int statusCode) {
        restAssuredThat(response -> response.statusCode(statusCode));
    }

    public static void assertResponseFieldEquals(String field, Object expectedValue) {
        restAssuredThat(response -> response.body(field, equalTo(expectedValue)));
    }

    public static void assertFieldContains(String field, String expectedSubstring) {
//        restAssuredThat(response -> response.body(field, contains(expectedSubstring)));
    }

}
