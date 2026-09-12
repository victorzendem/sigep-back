package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.application.dto.orgao.OrgaoPatchRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoResponse;
import br.com.sigep.sigep.application.dto.orgao.OrgaoUpdateRequest;
import br.com.sigep.sigep.domain.exception.OrgaoNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Orgao;
import br.com.sigep.sigep.infraestructure.persistency.repository.OrgaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgaoService {

    private final OrgaoRepository ORGAO_REPOSITORY;


    public OrgaoResponse cadastrar(OrgaoRequest orgaoRequest) {

        Orgao orgao = new Orgao(
                orgaoRequest.nome(),
                orgaoRequest.sigla(),
                orgaoRequest.cpnj()
        );
        ORGAO_REPOSITORY.save(orgao);

        return OrgaoResponse.from(orgao);
    }

    @Transactional
    public OrgaoResponse atualizar(Long id, OrgaoUpdateRequest request) {

        Orgao orgao = ORGAO_REPOSITORY.findById(id).orElseThrow(OrgaoNaoEncontradoException::new);

        orgao.atualizar(
                request.nome(),
                request.sigla(),
                request.sigla());

        return OrgaoResponse.from(orgao);
    }

    @Transactional
    public OrgaoResponse atualizarParciamente(Long id, OrgaoPatchRequest request){
        Orgao orgao = findById(id);

        orgao.atualizarParcialmente(
                request.nome(),
                request.sigla(),
                request.cnpj()
        );

        return OrgaoResponse.from(orgao);
    }


    @Transactional(readOnly = true)
    public Page<OrgaoResponse> listarTodos(Pageable pageable) {
        return ORGAO_REPOSITORY
                .findByAtivoTrue(pageable)
                .map(OrgaoResponse::from);
    }


    @Transactional(readOnly = true)
    public OrgaoResponse buscarPorId(Long id) {

        Orgao orgao = ORGAO_REPOSITORY
                .findById(id).orElseThrow(OrgaoNaoEncontradoException::new);

        return OrgaoResponse.from(orgao);
    }

    private Orgao findById(Long id) {
        return ORGAO_REPOSITORY.findById(id).orElseThrow(OrgaoNaoEncontradoException::new);
    }

    @Transactional
    public void desativar(Long id) {
        Orgao orgao = findById(id);
        orgao.desativar();
    }

    @Transactional
    public OrgaoResponse ativar(Long id){
        Orgao orgao = findById(id);
        orgao.ativar();
        return OrgaoResponse.from(orgao);
    }


}
