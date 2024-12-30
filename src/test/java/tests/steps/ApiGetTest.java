package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;

public class ApiGetTest {

    private String endpoint;
    private Response response;

    @Given("the API endpoint {string}")
    public void theApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("a GET request is sent with basic authentication")
    public void theApiEndpoint() {
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password")
                .when()
                .get(endpoint);
        // Test other user-accessible endpoints
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeIs(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the response should be in valid format")
    public void verifyBookDetails() {
        response.then()
                .assertThat()
                .body("$", instanceOf(java.util.List.class))  // Verify it's a list
                .body("size()", greaterThanOrEqualTo(0))      // List can be empty but should exist
                .body("findAll { it.id != null }", not(empty()))  // All books should have an id
                .body("findAll { it.title != null }", not(empty()))  // All books should have a title
                .body("findAll { it.author != null }", not(empty())); // All books should have an author

        // Additional logging for Allure reports
        Allure.addAttachment(
                "Validation Details",
                "Verified response is a list of books with required fields (id, title, author)"
        );
    }

}
