package Utils;

import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.serenitybdd.core.Serenity;

public class config {


    public static String getBaseUrl() {
        return ConfiguredEnvironment.getEnvironmentVariables()
                .getProperty("restapi.base.url", "https://petstore.swagger.io/v2");
    }

    public static int getConnectionTimeout() {
        return Integer.parseInt(ConfiguredEnvironment.getEnvironmentVariables().getProperty("rest-assured.timeout.connection", "5000"));
    }

    public static int getSocketTimeout() {
        return Integer.parseInt(ConfiguredEnvironment.getEnvironmentVariables().getProperty("rest-assured.timeout.socket", "5000"));
    }
}
