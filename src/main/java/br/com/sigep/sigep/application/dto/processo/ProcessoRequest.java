package br.com.sigep.sigep.application.dto.processo;

import br.com.sigep.sigep.domain.enums.PrioridadeProcesso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProcessoRequest(

        @NotBlank(message = "O título é obrigatório.")
        @Size(min = 5, max = 150, message = "O título deve ter entre 5 a 150 caracteres.")
        String titulo,

        @NotBlank(message =  "A descrição é obrigatória.")
        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
        String descricao,

        @NotNull(message = "A prioridade é obrigatória.")
        PrioridadeProcesso prioridade,

        @NotNull(message = "O ID do setor é obrigatório.")
        Long setorId,

        @NotNull(message = "O ID do usuário é obrigatório.")
        Long usuarioId

) {
}
