package tests.testData;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.DTO.Book;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.example.configs.TestConfig.*;

public class SetupData {

    public static void createBooks(){

        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee");
        Book book3 = new Book("1984", "George Orwell");

        addBook(book1);
        addBook(book2);
        addBook(book3);
        System.out.println("Added");

        clearDataBase();

    }

    public static void clearDataBase() {

        Response response = getBooks();


        if(response.getStatusCode() == 200){
            System.out.println(response.body().print());
        }

    }

    private static void addBook(Book book){
        Response response = given()
                .auth()
                .basic(ADMIN_USERNAME, PASSWORD)
                .contentType("application/json")
                .body(book)
                .when()
                .post("/books");
    }

    private static void deletBook(String id){
        Response deleteResponse = given()
                .auth()
                .basic(USER_USERNAME, PASSWORD)
                .when()
                .delete("/books" + "/" + id);
    }

    private static Response getBooks(){

        return given()
                .auth()
                .preemptive()
                .basic(ADMIN_USERNAME, PASSWORD)
                .when()
                .get("/books");
    }
}
