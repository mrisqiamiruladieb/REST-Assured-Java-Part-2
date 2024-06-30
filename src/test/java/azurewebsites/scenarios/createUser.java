package azurewebsites.scenarios;

import azurewebsites.data.DataUsers;
import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class createUser {

    // creates an instance of the class
    DataUsers dataUsers = new DataUsers();
    ApiEndpoints apiEndpoints = new ApiEndpoints();

    // Variables to store the request body
    private static int myIdRequest;
    private static String myUserNameRequest;
    private static String myPasswordRequest;

    // Variables to store the response body
    private static int myIdResponse;
    private static String myUserNameResponse;
    private static String myPasswordResponse;

    @Test
    public void createUserSuccess() {

        // get create user success request body
        JSONObject payload = dataUsers.createUserSuccess();

        // Save path file valid create user response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/validCreateUserResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .and()
                .body(payload.toString())
                .when()
                .post(apiEndpoints.getUsers()); // get endpoints to create user

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        // Store part of the request body to a variable
        myIdRequest = (int) payload.get("id");
        myUserNameRequest = (String) payload.get("userName");
        myPasswordRequest = (String) payload.get("password");

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
        Assert.assertEquals(myIdRequest, myIdResponse, "Check the same request and response");
        Assert.assertEquals(myUserNameRequest, myUserNameResponse, "Check the same request and response");
        Assert.assertEquals(myPasswordRequest, myPasswordResponse, "Check the same request and response");

        response.then()
                .assertThat()
                .body("id", is(greaterThan(0))) // Assert a non-empty variable
                .body("id", isA(Number.class)) // Assert a variable is a number
                .body("userName", not(empty())) // Assert a non-empty variable
                .body("userName", isA(String.class)) // Assert a variable is a string
                .body("password", not(empty())) // Assert a non-empty variable
                .body("password", isA(String.class)) // Assert a variable is a string
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }
}
