package Hooks;

import Utils.config;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;

public class Hooks {

    @Before
    public void setup() {
        System.out.println(">>> Setting up RestAssured config...");
        RestAssured.baseURI = config.getBaseUrl();
        RestAssured.config = RestAssured.config().httpClient(
                HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", config.getConnectionTimeout())
                        .setParam("http.socket.timeout", config.getSocketTimeout())
        );
    }
}
