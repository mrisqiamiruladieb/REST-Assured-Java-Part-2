package azurewebsites.scenarios;

import azurewebsites.endpoint.ApiEndpoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}
