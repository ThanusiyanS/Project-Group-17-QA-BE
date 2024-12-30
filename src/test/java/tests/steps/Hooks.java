package tests.steps;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import tests.testData.SetupData;

public class Hooks {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:7081/api";
        SetupData.createBooks();
    }
}
