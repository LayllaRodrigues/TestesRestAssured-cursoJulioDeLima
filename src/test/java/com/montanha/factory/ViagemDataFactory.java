package com.montanha.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montanha.Pojo.Viagem;

import java.io.FileInputStream;
import java.io.IOException;

public class ViagemDataFactory {

    public static Viagem criarViagem() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viagens.json"),Viagem.class);
    }

    public static Viagem criarViagemValida() throws IOException {
        return criarViagem();
    }

    public static Viagem criarViagemSemLocalDeDestino() throws IOException {
        Viagem viagemSemLocalDeDestino = criarViagem();
        viagemSemLocalDeDestino.setLocalDeDestino("");
        return viagemSemLocalDeDestino;
    }

}
