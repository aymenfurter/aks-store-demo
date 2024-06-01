package com.microsoft.azure;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class CustomHeaderFilterTest {

    @Test
    public void testCustomHeaderPresence() {
        given()
          .when().get("/")
          .then()
             .statusCode(200)
             .header("X-Version", equalTo("0.2"));
    }
}
