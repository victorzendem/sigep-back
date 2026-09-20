package br.com.sigep.sigep.application.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 50, message = "O nome deve contar no máximo 50 caracteres.")
        String nome,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 50, message = "O nome deve contar no máximo 50 caracteres.")
        String email,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "//d{11}", message = "Deve conter 11 dígitos.")
        String cpf,

        @NotBlank(message = "A senha é obrigatória.")
        String senha
) {
}
