package tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.DTO.Book;

import static org.hamcrest.core.IsEqual.equalTo;

import static org.hamcrest.MatcherAssert.assertThat;

public class apiCreateTest {

    private Response response;
    private String username;
    private String requestBody;

    Book newBook = new Book("Moneyy", "mee");

    Book duplicateBook = new Book("Moneyy", "mee");

    Book bookWithoutTitle = new Book(null, "mee");

    Book bookWithoutAuthor = new Book("NewBook", null);

    Book bookWithoutTitleAndAuthor = new Book(null, null);

    @Given("The user logged as a {string} in the system")
    public void theUserLoggedInTheSystem(String username) {
        this.username=username;
    }

    @When("I send a POST request to {string} with data")
    public void iSendAPostRequestWithData(String endpoint) {
        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post(endpoint);
    }

    @Then("the response status code of post should be {int}")
    public void theResponseStatusCodeOfPost(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the response should contain the book")
    public void theResponseShouldContainTheBook() {
        response.then()
                .assertThat()
                .body("title", equalTo(newBook.getTitle()))
                .body("author", equalTo(newBook.getAuthor()));

        Allure.addAttachment("Book Details", response.getBody().asString());
    }

    // Second scenario(bugs found)
    @When("I send a POST request to {string} without title")
    public void iSendAPostRequestWithMissingFields(String endpoint) {

       response= RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(bookWithoutTitle)
                .when()
                .post(endpoint);
    }

    @Then("the response status code of post without data should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String errorMessage) {
        response.then()
                .assertThat()
                .body("error", equalTo(errorMessage));

        Allure.addAttachment("Error Details", response.getBody().asString());
    }

    //Third scenario
    @When("I send a POST request to {string} with duplicate data {string} and {string}")
    public void iSendAPostRequestWithDuplicateData(String endpoint,String title, String author) {

        response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(duplicateBook)
                .when()
                .post(endpoint);
    }

    @Then("the response status code of post with duplicate data should be {int}")
    public void theResponseStatusCodeForDuplicateShouldBe(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the response should contain error message for duplicate {string}")
    public void theResponseShouldContainErrorMessageForDuplicate(String errorMessage) {
        response.then()
                .assertThat()
                .body("error", equalTo(errorMessage));

        Allure.addAttachment("Error Details", response.getBody().asString());
    }

    //4th test case
    @When("I send a POST request to {string} without author")
    public void iSendAPostRequestWithMissingAuthor(String endpoint) {

        response= RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(bookWithoutAuthor)
                .when()
                .post(endpoint);
    }

    @Then("the response status code of post without Author should be {int}")
    public void theResponseStatusCodeWithOutAuthor(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }

    @Then("the response should contain error message for no author {string}")
    public void theResponseShouldContainErrorMessageForWithoutAuthor(String errorMessage) {
        response.then()
                .assertThat()
                .body("error", equalTo(errorMessage));

        Allure.addAttachment("Error Details", response.getBody().asString());
    }

    //5th test case
    @When("I send a POST request to {string} with null body")
    public void iSendAPostRequestWithOutData(String endpoint) {

        response= RestAssured.given()
                .auth()
                .preemptive()
                .basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(bookWithoutTitleAndAuthor)
                .when()
                .post(endpoint);
    }

    @Then("the response status code for no data should be {int}")
    public void theResponseStatusCodeForEmpty(int statusCode) {
        assertThat(response.getStatusCode(), equalTo(statusCode));
    }
    @Then("the response should contain error message for no data {string}")
    public void theResponseShouldContainErrorMessageForEmpty(String errorMessage) {
        response.then()
                .assertThat()
                .body("error", equalTo(errorMessage));

        Allure.addAttachment("Error Details", response.getBody().asString());
    }
}
