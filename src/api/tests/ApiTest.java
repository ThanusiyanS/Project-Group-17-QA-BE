package api.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiTest {

    @BeforeClass
    public void setup() {
        // Set the base URI for all tests
        RestAssured.baseURI = "http://localhost:7081/api";
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
                .body("[0].title", equalTo("Walter W"));

    }

    @Test
    public void testAddBookWithAdmin() {
        // Admin credentials
        String newBook = "{\"title\": \"New Book 22\", \"author\": \"Author Nick\"}";

        given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .statusCode(201)
                .body("title", equalTo("New Book"))
                .body("author", equalTo("Author Name"));
    }

    @Test
    public void testGetBooksWithInvalidCredentials() {
        // Invalid credentials
        given()
                .auth().preemptive().basic("invalidUser", "wrongPassword")
                .when()
                .get("/books")
                .then()
                .log().status()
                .statusCode(401);

    }
}
