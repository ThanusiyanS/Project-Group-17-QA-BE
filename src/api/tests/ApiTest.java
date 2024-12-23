package api.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;


public class ApiTest {

    @BeforeClass
    public void setup() {
        // Set the base URI for all tests
        RestAssured.baseURI = "http://localhost:7081/api";
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testAddBookWithAdmin() {

        String newBook = "{ \"title\": \"New Book\", \"author\": \"Author Name\" }";

        given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .assertThat()
                .statusCode(201)
                .body("title", equalTo("New Book"))
                .body("author", equalTo("Author Name"));
    }

    // The tittle is mandatory according to the API but even if are not sending it is working fine, this is a issue  need to be fixed
    @Test
    public void testAddBookWithMissingTitle() {
        String newBook = "{ \"author\": \"This is Vinujah\" }";

        given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", containsString("Title is mandatory"));
    }
    @Test
    public void testAddBookWithMissingAuthor() {
        String newBook = "{ \"title\": \"This is Vinujah's story\" }";

        given()
                .auth().preemptive().basic("admin", "password")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", containsString("Author is mandatory"));
    }


    //Even with the invalid password admin is able to add the book
    @Test
    public void testAddBookWithInvalidCredentials() {
        String newBook = "{ \"title\": \"Invalid Book\", \"author\": \"Invalid Author\" }";

         given()
                .auth().preemptive().basic("admin", "wrongPassword")
                .contentType(ContentType.JSON)
                .body(newBook)
                .when()
                .post("/books")
                .then()
                .statusCode(401)
                .extract().body().asString();
    }

}
