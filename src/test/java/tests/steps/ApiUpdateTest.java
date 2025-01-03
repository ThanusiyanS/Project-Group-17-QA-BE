package tests.steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiUpdateTest {

    private String endpoint;
    private Response response;
    private Map<String, String> updateData;


    @Given("the API endpoint {string} for updating a book")
    public void theApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


    @When("a PUT request is sent with the following data:")
    public void sendPutRequestWithData(Map<String, String> data) {
        // Save the update data for validation
        this.updateData = data;

        // Send the PUT request with basic authentication and JSON body
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .put(endpoint);
    }


    @Then("the response status code of update should be {int}")
    public void theResponseStatusCodeIs(int statusCode) {
        // Assert that the response status code matches the expected value
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }


    @Then("the response should contain updated details")
    public void verifyUpdatedDetails() {
        // Verify the response body contains the updated title and author
        response.then()
                .assertThat()
                .body("title", equalTo(updateData.get("title")))
                .body("author", equalTo(updateData.get("author")));

        // Log the validation details in Allure reports
        Allure.addAttachment(
                "Updated Details",
                "Verified that the response contains updated title: " + updateData.get("title") +
                        " and author: " + updateData.get("author")
        );
    }

    @And("the response message should indicate {string}")
    public void theResponseMessageShouldIndicate(String arg0) {
    }


    //


    @When("a PUT request is sent with the invalid characters")
    public void sendPutRequestWithInvalidCharacters(Map<String, String> data) {
        // Save the update data for validation
        this.updateData = data;

        // Send the PUT request with basic authentication and JSON body
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .put(endpoint);
    }

    @Then("the response status code of update with invalid data should be {int}")
    public void theResponseStatusCodeforInvalidCharacters(int statusCode) {
        // Assert that the response status code matches the expected value
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @When("a PUT request is sent without author field:")
    public void sendPutRequestWithOutAuthor(Map<String, String> data) {
        // Save the update data for validation
        this.updateData = data;

        // Send the PUT request with basic authentication and JSON body
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .put(endpoint);
    }

    @Then("the response status code for no author should be {int}")
    public void theResponseStatusCodeforNoAuthor(int statusCode) {
        // Assert that the response status code matches the expected value
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @When("a PUT request is sent with unauthorized user:")
    public void sendPutRequestWithUnAuthorizedUser(Map<String, String> data) {
        // Save the update data for validation
        this.updateData = data;

        // Send the PUT request with basic authentication and JSON body
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("users", "passwords") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(data)
                .when()
                .put(endpoint);
    }
    @Then("the response status code of unauthorized user should be {int}")
    public void theResponseStatusCodeforUnauthorizedUser(int statusCode) {
        // Assert that the response status code matches the expected value
        assertThat(response.getStatusCode(), equalTo(statusCode));
}
}

