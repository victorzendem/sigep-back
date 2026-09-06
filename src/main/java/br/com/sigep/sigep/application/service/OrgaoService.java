package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.application.dto.orgao.OrgaoRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoResponse;
import br.com.sigep.sigep.domain.exception.OrgaoNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Orgao;
import br.com.sigep.sigep.infraestructure.persistency.repository.OrgaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgaoService {

    private final OrgaoRepository ORGAO_REPOSITORY;


    public OrgaoResponse cadastrar(OrgaoRequest orgaoRequest){

        Orgao orgao = new Orgao(
                orgaoRequest.nome(),
                orgaoRequest.sigla(),
                orgaoRequest.cpnj()
        );
        ORGAO_REPOSITORY.save(orgao);

        return OrgaoResponse.from(orgao);
    }

    public List<OrgaoResponse> listarTodos(){
        return ORGAO_REPOSITORY
                .findAll()
                .stream()
                .map(OrgaoResponse::from)
                .toList();
    }

    public OrgaoResponse buscarPorId(Long id){

        Orgao orgao = ORGAO_REPOSITORY
                .findById(id).orElseThrow(OrgaoNaoEncontradoException::new);

        return OrgaoResponse.from(orgao);
    }


}
