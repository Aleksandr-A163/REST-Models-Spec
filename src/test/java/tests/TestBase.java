package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.basePath = "/api";
    }
}