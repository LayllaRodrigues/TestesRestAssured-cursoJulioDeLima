package com.montanha.factory;


import com.montanha.Pojo.Viagens;

public class ViagemDataFactory {
    public static Viagens criarViagemValida() {
        Viagens viagens = new Viagens();

        viagens.setAcompanhante("Nelson");
        viagens.setDataPartida("2021-02-02");
        viagens.setDataRetorno("2021-02-03");
        viagens.setLocalDeDestino("Fortaleza");
        viagens.setRegiao("Norte");

        return viagens;
    }
}
