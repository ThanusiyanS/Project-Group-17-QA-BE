package tests.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.DTO.Book;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiUpdateTest {

    private String endpoint;
    private Response response;
    private Map requestData;


    public Book getData(String type){
        switch (type) {
            case "validData" :
                return new Book(1,"The Great ", "F. Scott Fitzgerald");
            case "invalidData" :
                return new Book(4,null,"mee");
            default:
                return null;
        }
    }

    @Given("the API endpoint {string} for updating a book")
    public void setApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("a PUT request is sent with the {string}")
    public void sendPutRequestWithDataX(String type) {
// this.requestData = data;
// System.out.println(data);
        Book validData = new Book(1,"The Great ", "F. Scott Fitzgerald");

// Sending a PUT request with basic authentication as a regular user
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(getData(type))
                .when()
                .put(endpoint);
    }

    @When("a PUT request is sent with the following data:")
    public void sendPutRequestWithData(DataTable dataTable) {
        var bookData = dataTable.asMaps(String.class, String.class).get(0);

        System.out.println(bookData);

// Sending a PUT request with basic authentication as a regular user
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password") // Replace with valid credentials
                .header("Content-Type", "application/json")
                .body(bookData)
                .when()
                .put(endpoint);
    }

    @And("the user is logged in as a regular user")
    public void userIsLoggedInAsRegularUser() {
// This step is implicitly handled by the authentication in the request.
        System.out.println("User is authenticated as a regular user.");
    }

    @Then("the response status code of update should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        assertThat(response.getStatusCode(), equalTo(expectedStatusCode));
    }

    @And("the response message should indicate {string}")
    public void verifyResponseMessage(String expectedMessage) {
        response.then()
                .assertThat()
                .body("error", equalTo(expectedMessage));

// Debug log for verification
        System.out.println("Response: " + response.getBody().asString());
    }

    @Then("the response should contain updated details")
    public void verifyUpdatedDetails() {
// Verify the response body contains the updated title and author
        response.then()
                .assertThat()
                .body("title", equalTo(requestData.get("title")))
                .body("author", equalTo(requestData.get("author")));

// Log the validation details in Allure reports
        Allure.addAttachment(
                "Updated Details",
                "Verified that the response contains updated title: " + requestData.get("title") +
                        " and author: " + requestData.get("author")
        );
    }

    @Then("the response status code of update should be {int}")
    public void theResponseStatusCodeOfUpdateShouldBe(int arg0) {
    }

    @When("a PUT request is sent with the invalid characters")
    public void sendPutRequestWithInvalidCharacters(Map<String, String> data) {
        // Save the update data for validation
        this.requestData = data;

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
        this.requestData = data;

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
        this.requestData = data;

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