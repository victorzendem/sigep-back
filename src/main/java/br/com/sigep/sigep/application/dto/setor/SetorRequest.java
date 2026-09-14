package br.com.sigep.sigep.application.dto.setor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SetorRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,
        @NotBlank(message = "A sigla é obrigatória.")
        String sigla,
        @NotNull(message = "O orgão é obrigatório.")
        Long orgaoId
) {
}
