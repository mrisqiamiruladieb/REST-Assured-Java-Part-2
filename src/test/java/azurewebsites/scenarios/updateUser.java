package azurewebsites.scenarios;

import azurewebsites.data.DataUsers;
import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class updateUser {

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

    @Test (priority = -1)
    public void successUpdateUser() {

        // get create user success request body
        JSONObject payload = dataUsers.successUpdateUser();

        // Save path file valid create user response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/validCreateUserResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", "3")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

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
                .body("userName", not(empty())) // Assert a non-empty variable
                .body("password", not(empty())) // Assert a non-empty variable
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateUpdateUserWithUnregisterIdPathParams() {

        // get create user success request body
        JSONObject payload = dataUsers.successUpdateUser();

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", "9999999999")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 404, "Check the status code is 404");
        Assert.assertEquals(response.getContentType(), "application/problem+json; charset=utf-8", "Check the content type is application/problem+json; charset=utf-8");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("type"), true, "Check the presence of property type");
        Assert.assertTrue(responseBody.contains("title"), "Check the presence of property title");
        Assert.assertTrue(responseBody.contains("status"), "Check the presence of property status");
        Assert.assertTrue(responseBody.contains("traceId"), "Check the presence of property traceId");
        Assert.assertTrue(responseBody.contains("errors"), "Check the presence of property errors");
        Assert.assertTrue(responseBody.contains("id"), "Check the presence of property id");

        response.then()
                .assertThat()
                .body("type", not(empty())) // Assert a non-empty variable
                .body("type", equalTo("https://tools.ietf.org/html/rfc7231#section-6.5.1"))
                .body("title", not(empty())) // Assert a non-empty variable
                .body("title", equalTo("One or more validation errors occurred."))
                .body("status", is(greaterThan(0))) // Assert a non-empty variable
                .body("status", equalTo(404))
                .body("traceId", not(empty())) // Assert a non-empty variable
                .body("errors", not(empty())) // Assert a non-empty variable
                .body("errors", hasKey("id")) // Asserts a variable has a specific key
                .body("errors.id", not(emptyArray())) // Assert a non-empty array variable
                .body("errors.id[0]", not(empty())) // Assert a non-empty variable
                .body("errors.id[0]", equalTo("The value '9999999999' is not valid."))
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateUpdateUserWithNonIntegerIdPathParams() {

        // get create user success request body
        JSONObject payload = dataUsers.successUpdateUser();

        // Save path file valid create user response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/badRequestGetUserByIdResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", "hello")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 400, "Check the status code is 400");
        Assert.assertEquals(response.getContentType(), "application/problem+json; charset=utf-8", "Check the content type is application/problem+json; charset=utf-8");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("type"), true, "Check the presence of property type");
        Assert.assertTrue(responseBody.contains("title"), "Check the presence of property title");
        Assert.assertTrue(responseBody.contains("status"), "Check the presence of property status");
        Assert.assertTrue(responseBody.contains("traceId"), "Check the presence of property traceId");
        Assert.assertTrue(responseBody.contains("errors"), "Check the presence of property errors");
        Assert.assertTrue(responseBody.contains("id"), "Check the presence of property id");

        response.then()
                .assertThat()
                .body("type", not(empty())) // Assert a non-empty variable
                .body("type", equalTo("https://tools.ietf.org/html/rfc7231#section-6.5.1"))
                .body("title", not(empty())) // Assert a non-empty variable
                .body("title", equalTo("One or more validation errors occurred."))
                .body("status", is(greaterThan(0))) // Assert a non-empty variable
                .body("status", equalTo(400))
                .body("traceId", not(empty())) // Assert a non-empty variable
                .body("errors", not(empty())) // Assert a non-empty variable
                .body("errors", hasKey("id")) // Asserts a variable has a specific key
                .body("errors.id", not(emptyArray())) // Assert a non-empty array variable
                .body("errors.id[0]", not(empty())) // Assert a non-empty variable
                .body("errors.id[0]", equalTo("The value 'hello' is not valid."))
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateUpdateUserWithBlankIdPathParams() {

        // get create user success request body
        JSONObject payload = dataUsers.successUpdateUser();

        // Save path file valid create user response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/badRequestGetUserByIdResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", " ")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 400, "Check the status code is 400");
        Assert.assertEquals(response.getContentType(), "application/problem+json; charset=utf-8", "Check the content type is application/problem+json; charset=utf-8");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");
        Assert.assertEquals(responseBody.contains("type"), true, "Check the presence of property type");
        Assert.assertTrue(responseBody.contains("title"), "Check the presence of property title");
        Assert.assertTrue(responseBody.contains("status"), "Check the presence of property status");
        Assert.assertTrue(responseBody.contains("traceId"), "Check the presence of property traceId");
        Assert.assertTrue(responseBody.contains("errors"), "Check the presence of property errors");
        Assert.assertTrue(responseBody.contains("id"), "Check the presence of property id");

        response.then()
                .assertThat()
                .body("type", not(empty())) // Assert a non-empty variable
                .body("type", equalTo("https://tools.ietf.org/html/rfc7231#section-6.5.1"))
                .body("title", not(empty())) // Assert a non-empty variable
                .body("title", equalTo("One or more validation errors occurred."))
                .body("status", is(greaterThan(0))) // Assert a non-empty variable
                .body("status", equalTo(400))
                .body("traceId", not(empty())) // Assert a non-empty variable
                .body("errors", not(empty())) // Assert a non-empty variable
                .body("errors", hasKey("id")) // Asserts a variable has a specific key
                .body("errors.id", not(emptyArray())) // Assert a non-empty array variable
                .body("errors.id[0]", not(empty())) // Assert a non-empty variable
                .body("errors.id[0]", equalTo("The value ' ' is invalid."))
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateUpdateUserWithRegisteredData() {

        // get create user success request body
        JSONObject payload = dataUsers.validateUpdateUserWithRegisteredData();

        // Save path file valid create user response schema
        String jsonSchemaFilePath = "src/test/java/azurewebsites/schema/Users/validCreateUserResponseSchema.json";

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", "4")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

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
        Assert.assertTrue(responseBody.contains("id"), "Check the presence of property id");
        Assert.assertEquals(responseBody.contains("userName"), true, "Check the presence of property userName");
        Assert.assertTrue(responseBody.contains("password"), "Check the presence of property password");
        Assert.assertEquals(myIdRequest, myIdResponse, "Check the same request and response");
        Assert.assertEquals(myUserNameRequest, myUserNameResponse, "Check the same request and response");
        Assert.assertEquals(myPasswordRequest, myPasswordResponse, "Check the same request and response");

        response.then()
                .assertThat()
                .body("id", is(greaterThan(0))) // Assert a non-empty variable
                .body("userName", not(empty())) // Assert a non-empty variable
                .body("password", not(empty())) // Assert a non-empty variable
                .body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePath))) // Validate the json schema response
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void updateUserValidationByFillingInAFewData() {

        // get create user success request body
        JSONObject payload = dataUsers.createUserValidationByFillingInAFewData();

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .and()
                .pathParams("id", "4")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 400, "Check the status code is 400");
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8; v=1.0", "Check the content type is application/json; charset=utf-8; v=1.0");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");

        response.then()
                .assertThat()
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateUpdateUserWithBlankRequestBodyField() {

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .contentType(ContentType.JSON)
                .body("{}")
                .and()
                .pathParams("id", "4")
                .when()
                .put(apiEndpoints.getUserById()); // get endpoints to update user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 400, "Check the status code is 400");
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8; v=1.0", "Check the content type is application/json; charset=utf-8; v=1.0");
        Assert.assertFalse(responseBody.isEmpty(), "Check the response body is not empty");

        response.then()
                .assertThat()
                 .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }
}
