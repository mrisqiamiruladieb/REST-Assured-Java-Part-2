package azurewebsites.scenarios;

import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class getUserById {

    // creates an instance of the class
    ApiEndpoints apiEndpoints = new ApiEndpoints();

    // Variables to store the response body
    private static int myIdResponse;
    private static String myUserNameResponse;
    private static String myPasswordResponse;

    @Test
    public void successGetUserById() {

        // Save path file valid get user by id response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/validCreateUserResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .and()
                .pathParams("id", "3")
                .when()
                .get(apiEndpoints.getUserById()); // get endpoints to get user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        // Store part of the response body to a variable
        JsonPath responseParsed = response.jsonPath();

        myIdResponse = responseParsed.getInt("id");
        myUserNameResponse = responseParsed.getString("userName");
        myPasswordResponse = responseParsed.getString("password");

        //Assert
        Assert.assertEquals(statusCode, 200, "Check the status code is 200");
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8; v=1.0", "Check the content type is application/json; charset=utf-8; v=1.0");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("id"), true, "Check the presence of property id");
        Assert.assertTrue(responseBody.contains("userName"), "Check the presence of property userName");
        Assert.assertTrue(responseBody.contains("password"), "Check the presence of property password");

        response.then()
                .assertThat()
                .body("id", is(greaterThan(0))) // Assert a non-empty variable
                .body("id", equalTo(myIdResponse))
                .body("userName", not(empty())) // Assert a non-empty variable
                .body("userName", equalTo(myUserNameResponse))
                .body("password", not(empty())) // Assert a non-empty variable
                .body("password", equalTo(myPasswordResponse))
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateGetDataWithUnregisterId() {

        // Save path file not found get user by id response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/notFoundGetUserByIdResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .and()
                .pathParams("id", "11")
                .when()
                .get(apiEndpoints.getUserById()); // get endpoints to get user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 404, "Check the status code is 404");
        Assert.assertEquals(response.getContentType(), "application/problem+json; charset=utf-8; v=1.0", "Check the content type is application/problem+json; charset=utf-8; v=1.0");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("type"), true, "Check the presence of property type");
        Assert.assertTrue(responseBody.contains("title"), "Check the presence of property title");
        Assert.assertTrue(responseBody.contains("status"), "Check the presence of property status");
        Assert.assertTrue(responseBody.contains("traceId"), "Check the presence of property traceId");

        response.then()
                .assertThat()
                .body("type", not(empty())) // Assert a non-empty variable
                .body("type", equalTo("https://tools.ietf.org/html/rfc7231#section-6.5.4"))
                .body("title", not(empty())) // Assert a non-empty variable
                .body("title", equalTo("Not Found"))
                .body("status", is(greaterThan(0))) // Assert a non-empty variable
                .body("status", equalTo(404))
                .body("traceId", not(empty())) // Assert a non-empty variable
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }
}
