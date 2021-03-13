package com.github.joelbars.maps.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class AccountResourceTest {

    @Test
    public void shouldGetBalanceOnDate() {
        LocalDate date = LocalDate.now();
        given().when()
                .get("/contacorrente/saldo?data=2020-04-05")
                .then()
                .body(is("{\"saldo\":27.50}"))
                .statusCode(200);
    }

    @Test
    public void shouldNotGetBalanceWithoutDate() {
        LocalDate date = LocalDate.now();
        given().when()
                .get("/contacorrente/saldo")
                .then()
                .body(is("É necessário definir \"data\" ou intervalo \"inicio\", \"fim\""))
                .statusCode(400);
    }

    @Test
    public void shouldNotGetBalanceWithWrongInterval() {
        LocalDate date = LocalDate.now();
        given().when()
                .get("/contacorrente/saldo?inicio=2020-04-05&fim=2020-03-05")
                .then()
                .body(is("Intervalo inválido (inicio após o fim)"))
                .statusCode(400);
    }

    @Test
    public void shouldCreditToAccount() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"valor\": 12.42,\"descricao\": \"alguma coisa\",\"data\": \"2020-02-28\"}")
                .post("/contacorrente/credito")
                .then()
                .statusCode(204);
    }

    @Test
    public void shouldDebitFromAccount() {
        given().when()
                .contentType(ContentType.JSON)
                .body("{\"valor\": 12.42,\"descricao\": \"alguma coisa\",\"data\": \"2020-02-28\"}")
                .post("/contacorrente/debito")
                .then()
                .statusCode(204);
    }

}