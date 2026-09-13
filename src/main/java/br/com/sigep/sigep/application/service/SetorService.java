package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.application.dto.setor.SetorPatchRequest;
import br.com.sigep.sigep.application.dto.setor.SetorRequest;
import br.com.sigep.sigep.application.dto.setor.SetorResponse;
import br.com.sigep.sigep.application.dto.setor.SetorUpdateRequest;
import br.com.sigep.sigep.domain.exception.OrgaoNaoEncontradoException;
import br.com.sigep.sigep.domain.exception.SetorJaExisteException;
import br.com.sigep.sigep.domain.exception.SetorNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Orgao;
import br.com.sigep.sigep.domain.model.Setor;
import br.com.sigep.sigep.infraestructure.persistency.repository.OrgaoRepository;
import br.com.sigep.sigep.infraestructure.persistency.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository SETOR_REPOSITORY;
    private final OrgaoRepository ORGAO_REPOSITORY;


    public SetorResponse cadastrar(SetorRequest setorRequest){

        Orgao orgao = ORGAO_REPOSITORY
                .findById(setorRequest.orgaoId()).orElseThrow(OrgaoNaoEncontradoException::new);

        if(SETOR_REPOSITORY.existsBySiglaAndOrgaoId(setorRequest.sigla(), setorRequest.orgaoId())){
            throw new SetorJaExisteException("Já existe um setor cadastrado com a sigla informada.");
        }
        if(SETOR_REPOSITORY.existsByNomeAndOrgaoId(setorRequest.nome(), setorRequest.orgaoId())){
            throw new SetorJaExisteException("Já existe um setor cadastrado com o nome informado.");
        }

        Setor setor = new Setor(
                setorRequest.nome(),
                setorRequest.sigla(),
                orgao
        );
        SETOR_REPOSITORY.save(setor);

        return SetorResponse.from(setor);
    }

    @Transactional
    public SetorResponse atualizar(Long id, SetorUpdateRequest request){

        Setor setor = findById(id);
        Long orgaoId = setor.getOrgao().getId();

        if(SETOR_REPOSITORY.existsBySiglaAndOrgaoIdAndNotId(request.sigla(), orgaoId, id)){
            throw new SetorJaExisteException("Já existe um setor cadastrado neste orgão com a sigla informada.");
        }

        if(SETOR_REPOSITORY.existsByNomeAndOrgaoIdAndNotId(request.nome(), orgaoId, id)){
            throw new SetorJaExisteException("Já existe um setor cadastrado neste orgão com o nome informado.");
        }

        setor.atualizar(
                request.nome(),
                request.sigla()
        );

        return SetorResponse.from(setor);
    }

    @Transactional
    public SetorResponse atualizarParcialmente(Long id, SetorPatchRequest request){


        Setor setor = findById(id);

        Long orgaoId = setor.getOrgao().getId();

        if(SETOR_REPOSITORY.existsBySiglaAndOrgaoIdAndNotId(request.sigla(), orgaoId, id)){
            throw new SetorJaExisteException("Já existe um setor cadastrado neste orgão com a sigla informada.");
        }
        if(SETOR_REPOSITORY.existsByNomeAndOrgaoIdAndNotId(request.nome(), orgaoId, id)){
            throw new SetorJaExisteException("Já existe um setor cadastrado neste orgão com o nome informado.");
        }

        setor.atualizarParcialmente(
                request.nome(),
                request.sigla()

        );
        return SetorResponse.from(setor);
    }




    @Transactional(readOnly = true)
    public SetorResponse buscarPorId(Long id){
        Setor setor = SETOR_REPOSITORY.findById(id).orElseThrow(SecurityException::new);

        return SetorResponse.from(setor);
    }

    private Setor findById(Long id){
        return SETOR_REPOSITORY.findById(id).orElseThrow(SetorNaoEncontradoException::new);
    }


    @Transactional(readOnly = true)
    public Page<SetorResponse> listarTodos(Pageable pageable){
        return SETOR_REPOSITORY.findByAtivoTrue(pageable)
                .map(SetorResponse::from);
    }
}



