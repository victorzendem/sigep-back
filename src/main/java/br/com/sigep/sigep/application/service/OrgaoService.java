package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.domain.model.Orgao;
import br.com.sigep.sigep.infraestructure.persistency.repository.OrgaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoService {

    private final OrgaoRepository ORGAO_REPOSITORY;


    public Orgao cadastrar(Orgao orgao){
        return ORGAO_REPOSITORY.save(orgao);
    }


}
