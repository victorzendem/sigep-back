package br.com.sigep.sigep.application.dto.orgao;

import br.com.sigep.sigep.domain.model.Orgao;

public record OrgaoResponse(
    Long id,
    String nome,
    String sigla,
    String cnpj,
    boolean ativo
) {

    public static OrgaoResponse from(Orgao orgao){
        return new OrgaoResponse(
                orgao.getId(),
                orgao.getNome(),
                orgao.getSigla(),
                orgao.getCnpj(),
                orgao.isAtivo());
    }
}
