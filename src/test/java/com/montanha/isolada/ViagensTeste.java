package com.montanha.isolada;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ViagensTeste {
    @Test
    public void testCadastroDeViagemVaidaRetornaSucesso() {
        // Configurações Rest-assured
        baseURI="http://localhost";
        port=8089;
        basePath="/api";

        String token = given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                    "  \"email\": \"admin@email.com\",\n" +
                    "  \"senha\": \"654321\"\n" +
                    "}")
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");

        given()
            .contentType (ContentType.JSON)
            .body("{\n" +
                    "  \"acompanhante\": \"Nelson\",\n" +
                    "  \"dataPartida\": \"2021-02-02\",\n" +
                    "  \"dataRetorno\": \"2021-02-03\",\n" +
                    "  \"localDeDestino\": \"Fortaleza\",\n" +
                    "  \"regiao\": \"Norte\"\n" +
                    "}")
            .header("Authorization",token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body ("data.localDeDestino", equalTo("Osasco"));
    }
}

