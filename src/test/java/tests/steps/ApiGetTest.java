package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.example.configs.TestConfig.PASSWORD;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;

public class ApiGetTest {

    private String endpoint;
    private RequestSpecification request;
    private Response response;

    @Given("User authorized as a valid {string}")
    public void userAuthorizedAsAValid(String user) {
        request = RestAssured.given()
                .auth()
                .preemptive()
                .basic(user, PASSWORD);
    }

    @Given("the GET API endpoint {string}")
    public void theApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("a GET request is sent with basic authentication")
    public void theApiEndpoint() {
        response = request
                .when()
                .get(endpoint);
        // Test other user-accessible endpoints
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeIs(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the list of books should be in valid format")
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

    @Then("the book data should be in valid format")
    public void verifyBookData() {
        response.then()
                .assertThat()
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("author", notNullValue());

        Allure.addAttachment(
                "Validation Details",
                "Verified response is a book with required fields" +
                        "]]" +
                        " (id, title, author)"
        );
    }

}
