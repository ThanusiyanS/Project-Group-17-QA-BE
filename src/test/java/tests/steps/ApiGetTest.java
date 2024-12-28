package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

    @Then("the first book's title should be {string}")
    public void theFirstBookTitleIs(String title) {
        assertThat(response.jsonPath().getString("[0].title"), equalTo(title));
    }

}
