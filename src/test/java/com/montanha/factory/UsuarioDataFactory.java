package com.montanha.factory;

import com.montanha.Pojo.Usuario;

public class UsuarioDataFactory {
    public static Usuario criarUsarioAdministrador() {
        Usuario usuarioAdministrador = new Usuario();

        usuarioAdministrador.setEmail("admin@email.com");
        usuarioAdministrador.setSenha("654321");

        return usuarioAdministrador;
    }

}
