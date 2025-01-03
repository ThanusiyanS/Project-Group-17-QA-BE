package tests.steps;

        import io.cucumber.java.en.Given;
        import io.cucumber.java.en.When;
        import io.cucumber.java.en.Then;
        import io.cucumber.java.en.And;
        import io.qameta.allure.Allure;
        import io.restassured.RestAssured;
        import io.restassured.response.Response;

        import java.util.Map;

        import static org.hamcrest.CoreMatchers.equalTo;
        import static org.hamcrest.MatcherAssert.assertThat;

public class ApiBookUpdateTest {

    private String endpoint;
    private Response response;
    private Map<String, String> requestData;

    @Given("the API endpoint {string} for updating a book")
    public void setApiEndpoint(String endpoint) {
        this.endpoint = endpoint;
        System.out.println("Endpoint set to: " + endpoint);
    }

    @When("a PUT request is sent with the following data:")
    public void sendPutRequestWithData(Map<String, String> data) {
        this.requestData = data;

        // Log the request data
        System.out.println("Sending PUT request to " + endpoint + " with data: " + data);

        // Send a PUT request with basic authentication
        try {
            response = RestAssured.given()
                    .auth()
                    .preemptive()
                    .basic("regularUser", "password") // Replace with valid credentials
                    .header("Content-Type", "application/json")
                    .body(data)
                    .when()
                    .put(endpoint);

            // Attach request and response details to Allure
            Allure.addAttachment("Request Data", data.toString());
            Allure.addAttachment("Response Body", response.getBody().asString());
        } catch (Exception e) {
            System.err.println("Error sending PUT request: " + e.getMessage());
            throw e;
        }
    }

    @And("the user is logged in as a regular user")
    public void userIsLoggedInAsRegularUser() {
        // Log authentication information
        System.out.println("User is authenticated as a regular user.");
    }

    @Then("the response status code of update should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        if (response == null) {
            System.err.println("Response is null, cannot verify status code.");
            assertThat("Response should not be null", false);
        } else {
            System.out.println("Verifying response status code...");
            assertThat("Status code mismatch", response.getStatusCode(), equalTo(expectedStatusCode));
        }
    }

    @And("the response message should indicate {string}")
    public void verifyResponseMessage(String expectedMessage) {
        if (response == null) {
            System.err.println("Response is null, cannot verify message.");
            assertThat("Response should not be null", false);
        } else {
            System.out.println("Verifying response message...");
            response.then()
                    .assertThat()
                    .body("error", equalTo(expectedMessage));
            System.out.println("Response: " + response.getBody().asString());
        }
    }

    @Then("the response should contain updated details")
    public void verifyUpdatedDetails() {
        if (response == null) {
            System.err.println("Response is null, cannot verify updated details.");
            assertThat("Response should not be null", false);
        } else {
            System.out.println("Verifying updated details...");
            response.then()
                    .assertThat()
                    .body("title", equalTo(requestData.get("title")))
                    .body("author", equalTo(requestData.get("author")));

            // Attach the validation results to Allure
            Allure.addAttachment(
                    "Updated Details",
                    "Verified that the response contains updated title: " + requestData.get("title") +
                            " and author: " + requestData.get("author")
            );
        }
    }
}
