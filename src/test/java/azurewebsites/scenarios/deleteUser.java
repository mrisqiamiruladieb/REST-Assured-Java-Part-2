package azurewebsites.scenarios;

import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class deleteUser {

    // creates an instance of the class
    ApiEndpoints apiEndpoints = new ApiEndpoints();

    @Test
    public void successDeleteUser() {

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .and()
                .pathParams("id", "1")
                .when()
                .delete(apiEndpoints.getUserById()); // get endpoints to delete user by id

        System.out.println("--------------------Response----------------------");

        // Get response code
        int statusCode = response.getStatusCode();
        System.out.println("Response Status Codes: " + statusCode);

        // Get response body
        String responseBody = response.thenReturn().asPrettyString();
        System.out.println("Response Body:\n" + responseBody);

        //Assert
        Assert.assertEquals(statusCode, 200, "Check the status code is 200");
        Assert.assertTrue(responseBody.isEmpty(), "Check the response body is empty");

        response.then()
                .assertThat()
                .body(is(emptyOrNullString())) // Assert response body is empty or null string
                .time(lessThan(5000L)); // Validate the response time – in milliseconds
    }

    @Test
    public void validateDeleteUserWithUnregisterId() {

        System.out.println("--------------------Request----------------------");

        Response response = given().log().all()
                .accept("*/*")
                .and()
                .pathParams("id", "9999999999")
                .when()
                .delete(apiEndpoints.getUserById()); // get endpoints to delete user by id

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
}
