package com.github.joelbars.maps.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWithIgnoringCase;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class MarketValueResourceTest {

    @Test
    public void shouldGetMarketValusForAsset() {
        given().when()
                .get("/valormercado/ATIVO0")
                .then()
                .statusCode(200)
                .body(is("[{\"ativo\":\"ATIVO0\",\"data\":\"2020-01-02\",\"valor\":6.0}]"));
    }

    @Test
    public void shouldGetMarketValusForAssetOnDate() {
        given().when()
                .get("/valormercado/ATIVO0?data=2020-01-02")
                .then()
                .statusCode(200)
                .body(is("[{\"ativo\":\"ATIVO0\",\"data\":\"2020-01-02\",\"valor\":6.0}]"));
    }

    @Test
    public void shouldAddMarketValue() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"ativo\":\"ATIVO0\",\"data\":\"2020-05-12\",\"valor\":6.0}")
                .post("/valormercado")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldNotAddInvalidMarketValue() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"ativo\":\"ATIVO0\",\"valor\":6.0}")
                .post("/valormercado")
                .then()
                .statusCode(400)
                .body(endsWithIgnoringCase("Data é um campo obrigatório"));
    }

    @Test
    public void shouldNotDeleteWithoutValidDate() {
        given().when()
                .contentType(ContentType.JSON)
                .delete("/valormercado/ATIVO100")
                .then()
                .statusCode(400)
                .body(is("A data é obrigatória"));
    }

    @Test
    public void shouldDeleteMarketValue() {
        given().when()
                .contentType(ContentType.JSON)
                .delete("/valormercado/ATIVO100?data=2020-01-02")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldNotDeleteIfNotFound() {
        given().when()
                .contentType(ContentType.JSON)
                .delete("/valormercado/ATIVO101?data=2020-04-02")
                .then()
                .statusCode(404);
    }

}