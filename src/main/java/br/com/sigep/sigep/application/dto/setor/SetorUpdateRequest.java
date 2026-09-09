package br.com.sigep.sigep.application.dto.setor;

import br.com.sigep.sigep.domain.model.Orgao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SetorUpdateRequest(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(max = 40, message = "O nome deve conter no máximo 40 caracteres.")
        String nome,


        @NotBlank(message = "A sigla é obrigatória")
        @Size(max = 15, message = "A sigla deve conter no máximo 15 caracteres")
        String sigla,

        @NotBlank(message = "O orgão é obrigatório.")
        Orgao orgao

) {
}
