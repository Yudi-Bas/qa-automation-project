package api;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test(description = "GET posts/1 success")
    public void testGetPost() {
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }

    @Test(description = "GET users list")
    public void testGetUsers() {
        given()
            .baseUri("https://jsonplaceholder.typicode.com")
        .when()
            .get("/users")
        .then()
            .statusCode(200);
    }
}
