package br.com.sigep.sigep.application.dto.orgao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrgaoUpdateRequest(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 50, message  = "O nome deve conter no máximo 50 caracteres.")
        String nome,

        @NotBlank(message = "A sigla é obrigatória." )
        @Size(max = 20, message = "A sigla deve conter no máximo 20 caracteres")
        String sigla,

        @NotBlank(message = "O CNPJ é obrigatório.")
        @Pattern(regexp = "//d{14}", message = "O cnpj deve conter 14 dígitos.")
        String cnpj


) {
}
