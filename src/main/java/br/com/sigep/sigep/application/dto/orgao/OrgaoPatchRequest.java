package br.com.sigep.sigep.application.dto.orgao;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OrgaoPatchRequest(

        @Size(max=100, message="O nome deve ter no máximo 100 caracteres.")
        String nome,

        @Size(max=20, message="A sigla deve ter no máximo 20 caracteres.")
        String sigla,

        @Pattern(regexp = "//{14}", message = "O CNPJ deve ter no máximo 14 dígitos.")
        String cnpj
) {
}
