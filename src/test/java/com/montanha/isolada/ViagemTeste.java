package com.montanha.isolada;

import com.montanha.Pojo.Usuario;
import com.montanha.Pojo.Viagem;
import com.montanha.factory.UsuarioDataFactory;
import com.montanha.factory.ViagemDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class ViagemTeste {

    private String token;

    @Before
    public void setUp() {
        // Configurações Rest-assured
        baseURI="http://localhost";
        port=8089;
        basePath="/api";

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsarioAdministrador();

        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
                .when()
                .post("/v1/auth")
                .then()
                .extract()
                .path("data.token");

    }

    @Test
    public void testCadastroDeViagemVaidaRetornaSucesso() throws IOException {

        Viagem viagem = ViagemDataFactory.criarViagemValida();

        given()
            .contentType (ContentType.JSON)
            .body(viagem)
            .header("Authorization",token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body ("data.localDeDestino", equalTo("Fortaleza"));
    }

    @Test
    public void testViagemSemLocalDeDestino() throws IOException {
        Viagem viagemSemLocalDeDestino = ViagemDataFactory.criarViagemSemLocalDeDestino();

        given()
            .contentType(ContentType.JSON)
            .body(viagemSemLocalDeDestino)
            .header("Authorization",token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(400);
    }
}

