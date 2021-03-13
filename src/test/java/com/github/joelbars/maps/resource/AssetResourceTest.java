package com.github.joelbars.maps.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AssetResourceTest {

    @Test
    public void shouldGetAssets() {
        given().when()
                .get("/ativos")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldAddAsset() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"ATIVO155\",\"tipo\":\"RF\",\"dataEmissao\":\"2020-01-01\",\"dataVencimento\":\"2020-06-01\"}")
                .post("/ativos")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldUpdateAsset() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"ATIVO99\",\"tipo\":\"RF\",\"dataEmissao\":\"2020-01-01\",\"dataVencimento\":\"2020-06-01\"}")
                .put("/ativos/ATIVO121")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldNotUpdateAssetWhenNotFound() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"ATIVO199\",\"tipo\":\"RF\",\"dataEmissao\":\"2020-01-01\",\"dataVencimento\":\"2020-06-01\"}")
                .put("/ativos/ATIVO199")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldNotDeleteAssetIfHasReferences() {
        given()
                .when().delete("/ativos/ATIVO122")
                .then()
                .statusCode(400);
    }

}