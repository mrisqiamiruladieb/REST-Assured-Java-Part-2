package azurewebsites.scenarios;

import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class getUsers {

    // creates an instance of the class
    ApiEndpoints apiEndpoints = new ApiEndpoints();

    @Test
    public void getUsersSuccess() {

        // Save path file valid get users response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/validGetUsersResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept(ContentType.JSON)
                .when()
                .get(apiEndpoints.getUsers()); // get endpoints to get users

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        // Assert
        Assert.assertEquals(statusCode, 200, "Check the status code is 200");
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8; v=1.0", "Check the content type is application/json; charset=utf-8; v=1.0");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("id"), true, "Check the presence of property id");
        Assert.assertTrue(responseBody.contains("userName"), "Check the presence of property userName");
        Assert.assertTrue(responseBody.contains("password"), "Check the presence of property password");

        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }
}
