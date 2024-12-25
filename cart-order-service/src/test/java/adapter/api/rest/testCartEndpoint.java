package adapter.api.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class testCartEndpoint {

    // Proof of concept for testing the REST API
    @Test
    void testTest(){

        given()
                .when().get("/cart")
                .then()
                .statusCode(200);
    }

}
