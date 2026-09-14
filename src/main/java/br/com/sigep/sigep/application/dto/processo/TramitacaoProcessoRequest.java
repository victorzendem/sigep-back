package br.com.sigep.sigep.application.dto.processo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TramitacaoProcessoRequest(

        @NotNull(message = "O ID do novo setor é obrigatório.")
        Long novoSetorId,

        @NotBlank(message = "O despacho/motivo da tramitação é obritatório.")
        @Size(min = 5, max  = 1000, message = "O despacho deve ter entre 5 e 1000 caracteres.")
        String despacho
) {
}
