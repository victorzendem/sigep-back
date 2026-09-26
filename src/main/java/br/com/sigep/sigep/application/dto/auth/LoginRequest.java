package br.com.sigep.sigep.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message="O email é obrigatório.")
        @Email(message="Fomato de email inválido.")
        String email,

        @NotBlank(message="A senha é obrigatória.")
        String senha
) {
}
