package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.example.configs.TestConfig.PASSWORD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiDeleteTest {
    private Response response;
    private String username;

    @Given("the book with ID {int} exists in the system")
    public void theBookWithIDExistsInTheSystem(int id) {
         response = RestAssured.given()
                .auth().basic("admin", "password")
                .get("/books/" + id);
        assertThat(response.getStatusCode(), equalTo(200));
    }

    @When("the user sends a DELETE request to {string}")
    public void theUserSendsADELETERequestTo(String endpoint) {
        response = RestAssured.given()
                .auth().basic(username, PASSWORD)
                .delete(endpoint);
    }

    @Then("the response status code of delete should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertThat("Response is null!", response, equalTo(response));
        assertThat(response.getStatusCode(), equalTo(statusCode)); 
    }

    @Then("the book with ID {int} should not exist in the system")
    public void theBookWithIDShouldNotExistInTheSystem(int id) {
        Response response = RestAssured.given()
                .auth().basic("admin", "password")
                .get("/books/" + id);
        assertThat(response.getStatusCode(), equalTo(404));
    }

    @Given("the user logged as a {string} in the system")
    public void theUserLoggedInTheSystem(String username) {
        this.username=username;
    }
    
}
