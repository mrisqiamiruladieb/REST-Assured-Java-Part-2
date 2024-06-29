package azurewebsites.endpoint;

import azurewebsites.config.TestConfig;

public class ApiEndpoints {

    TestConfig config = new TestConfig();
    String baseURL = config.getBaseUrl();

    public String getUsers() {
        return baseURL + "/Users";
    }
}
