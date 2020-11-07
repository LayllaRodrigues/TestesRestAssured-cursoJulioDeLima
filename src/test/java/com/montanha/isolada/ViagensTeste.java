package com.montanha.isolada;

import com.montanha.Pojo.Usuario;
import com.montanha.Pojo.Viagens;
import com.montanha.factory.UsuarioDataFactory;
import com.montanha.factory.ViagemDataFactory;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class ViagensTeste {

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
    public void testCadastroDeViagemVaidaRetornaSucesso() {

        Viagens viagens = ViagemDataFactory.criarViagemValida();

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

