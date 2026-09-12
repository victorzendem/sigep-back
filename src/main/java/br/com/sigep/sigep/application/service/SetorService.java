package br.com.sigep.sigep.application.service;

import br.com.sigep.sigep.application.dto.setor.SetorPatchRequest;
import br.com.sigep.sigep.application.dto.setor.SetorRequest;
import br.com.sigep.sigep.application.dto.setor.SetorResponse;
import br.com.sigep.sigep.application.dto.setor.SetorUpdateRequest;
import br.com.sigep.sigep.domain.exception.SetorNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Setor;
import br.com.sigep.sigep.infraestructure.persistency.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository SETOR_REPOSITORY;


    public SetorResponse cadastrar(SetorRequest setorRequest){

        Setor setor = new Setor(
                setorRequest.nome(),
                setorRequest.sigla(),
                setorRequest.orgao()
        );
        SETOR_REPOSITORY.save(setor);

        return SetorResponse.from(setor);
    }

    @Transactional
    public SetorResponse atualizar(Long id, SetorUpdateRequest request){

        Setor setor = SETOR_REPOSITORY.findById(id).orElseThrow(SetorNaoEncontradoException::new);
        setor.atualizar(
                request.nome(),
                request.sigla(),
                request.orgao()
        );

        return SetorResponse.from(setor);
    }

    @Transactional
    public SetorResponse atualizarParcialmente(Long id, SetorPatchRequest request){

        Setor setor = findById(id);
        setor.atualizarParcialmente(
                request.nome(),
                request.sigla(),
                request.orgao()
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
    public List<SetorResponse> listarTodos(){
        return SETOR_REPOSITORY.findAll().stream().map(SetorResponse::from).toList();
    }
}



