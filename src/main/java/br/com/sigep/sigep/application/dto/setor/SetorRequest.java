package br.com.sigep.sigep.application.dto.setor;

import br.com.sigep.sigep.domain.model.Orgao;

public record SetorRequest(
        String nome,
        String sigla,
        Orgao orgao
) {
}
