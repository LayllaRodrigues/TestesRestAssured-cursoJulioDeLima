package com.montanha.isolada;

import com.montanha.Pojo.Usuario;
import com.montanha.Pojo.Viagem;
import com.montanha.config.Configuracoes;
import com.montanha.factory.UsuarioDataFactory;
import com.montanha.factory.ViagemDataFactory;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.hamcrest.Matchers.equalTo;

public class ViagemTeste {

    private String token;

    @Before
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI= configuracoes.baseURI();
        port= configuracoes.port();
        basePath= configuracoes.basePath();

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
    public void testCadastroDeViagemValidaRetornaSucesso() throws IOException {

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
    @Test
    public void testCadastroDeViagemValidaContrato() throws IOException {

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
                .body(matchesJsonSchemaInClasspath("schemas/postV1ViagensViagemValida.json"));
    }
}

