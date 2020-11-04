package com.montanha.isolada;

import com.montanha.Pojo.Usuario;
import com.montanha.Pojo.Viagens;
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

        Usuario usuarioAdministrador = new Usuario();
        usuarioAdministrador.setEmail("admin@email.com");
        usuarioAdministrador.setSenha("654321");

        String token = given()
            .contentType(ContentType.JSON)
            .body(usuarioAdministrador)
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");

        Viagens viagens = new Viagens();
        viagens.setAcompanhante("Nelson");
        viagens.setDataPartida("2021-02-02");
        viagens.setDataRetorno("2021-02-03");
        viagens.setLocalDeDestino("Fortaleza");
        viagens.setRegiao("Norte");

        given()
            .contentType (ContentType.JSON)
            .body(viagens)
            .header("Authorization",token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body ("data.localDeDestino", equalTo("Fortaleza"));
    }
}

