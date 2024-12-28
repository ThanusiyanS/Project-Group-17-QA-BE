package tests.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiCreateTest {



    @Test
    public void testAddBookWithAdmin() {
        // Admin credentials
        String newBook = "{\"title\": \"New Book 111\", \"author\": \"Nick P\"}";

        given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .statusCode(201);
    }


}
