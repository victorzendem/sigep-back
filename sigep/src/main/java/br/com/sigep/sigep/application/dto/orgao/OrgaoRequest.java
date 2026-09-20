package br.com.sigep.sigep.application.dto.orgao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrgaoRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 100, message = "O nome deve conter no máximo 100 caracteres.")
        String nome,

        @NotBlank(message = "A sigla é obrigatório.")
        @Size(max = 20, message = "A sigla deve conter no máximo 20 caracteres.")
        String sigla,

        @NotBlank(message = "O CNPJ é obrigatório.")
        @Pattern(regexp = "//d{14}", message = "Deve conter 14 dígitos")
        String cpnj
) {
}
