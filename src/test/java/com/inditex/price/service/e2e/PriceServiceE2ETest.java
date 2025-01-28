package com.inditex.price.service.e2e;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceServiceE2ETest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost";

    @Test
    void testPriceForProductAt10AMOn14thJune() {
        Response response = given()
                .baseUri(baseUrl + ":" + port)
                .contentType(ContentType.JSON)
                .param("productId", 35455)
                .param("brandId", 1)
                .param("dateTime", "2020-06-14T10:00:00")
                .when()
                .get("/prices");

        response.then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(1))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("finalPrice", equalTo(35.50F))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testPriceForProductAt4PMOn14thJune() {
        Response response = given()
                .baseUri(baseUrl + ":" + port)
                .contentType(ContentType.JSON)
                .param("productId", 35455)
                .param("brandId", 1)
                .param("dateTime", "2020-06-14T16:00:00")
                .when()
                .get("/prices");

        response.then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(2))
                .body("startDate", equalTo("2020-06-14T15:00:00"))
                .body("endDate", equalTo("2020-06-14T18:30:00"))
                .body("finalPrice", equalTo(25.45F))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testPriceForProductAt9PMOn14thJune() {
        Response response = given()
                .baseUri(baseUrl + ":" + port)
                .contentType(ContentType.JSON)
                .param("productId", 35455)
                .param("brandId", 1)
                .param("dateTime", "2020-06-14T21:00:00")
                .when()
                .get("/prices");

        response.then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(1))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("finalPrice", equalTo(35.50F))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testPriceForProductAt10AMOn15thJune() {
        Response response = given()
                .baseUri(baseUrl + ":" + port)
                .contentType(ContentType.JSON)
                .param("productId", 35455)
                .param("brandId", 1)
                .param("dateTime", "2020-06-15T10:00:00")
                .when()
                .get("/prices");

        response.then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(3))
                .body("startDate", equalTo("2020-06-15T00:00:00"))
                .body("endDate", equalTo("2020-06-15T11:00:00"))
                .body("finalPrice", equalTo(30.50F))
                .body("currency", equalTo("EUR"));
    }

    @Test
    void testPriceForProductAt9PMOn16thJune() {
        Response response = given()
                .baseUri(baseUrl + ":" + port)
                .contentType(ContentType.JSON)
                .param("productId", 35455)
                .param("brandId", 1)
                .param("dateTime", "2020-06-16T21:00:00")
                .when()
                .get("/prices");

        response.then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(4))
                .body("startDate", equalTo("2020-06-15T16:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("finalPrice", equalTo(38.95F))
                .body("currency", equalTo("EUR"));
    }
}