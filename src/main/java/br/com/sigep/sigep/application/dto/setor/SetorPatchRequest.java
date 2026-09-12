package br.com.sigep.sigep.application.dto.setor;

import br.com.sigep.sigep.domain.model.Orgao;
import jakarta.validation.constraints.Size;

public record SetorPatchRequest(

        @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres.")
        String nome,

        @Size(max = 15, message = "A sigla deve ter no máximo 15 caracteres.")
        String sigla,
        Orgao orgao
) {
}
