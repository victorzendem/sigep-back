package br.com.sigep.sigep.application.dto.setor;

import br.com.sigep.sigep.domain.model.Orgao;
import br.com.sigep.sigep.domain.model.Setor;

public record SetorResponse(
        Long id,
        String nome,
        String sigla,
        Orgao orgao,
        boolean ativo
) {

    public static SetorResponse from (Setor setor){
        return new SetorResponse(
                setor.getId(),
                setor.getNome(),
                setor.getSigla(),
                setor.getOrgao(),
                setor.isAtivo()
        );
    }
}
