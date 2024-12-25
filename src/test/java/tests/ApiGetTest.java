package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class ApiGetTest {


    @Test
    public void testUserAccessToPermittedEndpoints() {
        RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password")
                .when()
                .get("/books")
                .then()
                .statusCode(200);
        // Test other user-accessible endpoints
    }
    @Test
    public void testGetBooks() {

        // Sending GET Request to retrieve books
        RestAssured.given()
                .auth()
                .preemptive()
                .basic("user", "password")
                .get("/books")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("[0].title", equalTo("New Book 3"))
                .log().all();

    }
}
