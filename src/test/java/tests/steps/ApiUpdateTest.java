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
    private Map<String, String> requestData;


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
//        this.requestData = data;
//        System.out.println(data);
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

    @Then("the response status code of update should be {int}")
    public void theResponseStatusCodeOfUpdateShouldBe(int arg0) {
    }
}