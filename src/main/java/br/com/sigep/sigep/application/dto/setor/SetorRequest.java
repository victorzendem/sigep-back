package br.com.sigep.sigep.application.dto.setor;

import br.com.sigep.sigep.domain.model.Orgao;
import jakarta.validation.constraints.NotBlank;

public record SetorRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,
        @NotBlank(message = "A sigla é obrigatória.")
        String sigla,
        @NotBlank(message = "O orgão é obrigatório.")
        Orgao orgao
) {
}
