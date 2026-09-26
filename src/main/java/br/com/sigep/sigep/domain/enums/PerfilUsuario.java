package br.com.sigep.sigep.domain.enums;


import lombok.Getter;

@Getter
public enum PerfilUsuario {
     ADMIN("ROLE_ADMIN"),
    USUARIO("ROLE_USUARIO");

    private final String role;

    PerfilUsuario(String role){
        this.role = role;
    }
}
