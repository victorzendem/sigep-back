package br.com.sigep.sigep.application.service;


import br.com.sigep.sigep.application.dto.processo.ProcessoRequest;
import br.com.sigep.sigep.application.dto.processo.ProcessoResponse;
import br.com.sigep.sigep.domain.exception.ProcessoNaoEncontradoException;
import br.com.sigep.sigep.domain.exception.SetorNaoEncontradoException;
import br.com.sigep.sigep.domain.exception.UsuarioNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Processo;
import br.com.sigep.sigep.domain.model.Setor;
import br.com.sigep.sigep.domain.model.Usuario;
import br.com.sigep.sigep.infraestructure.persistency.repository.ProcessoRepository;
import br.com.sigep.sigep.infraestructure.persistency.repository.SetorRepository;
import br.com.sigep.sigep.infraestructure.persistency.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class ProcessoService {

    private final ProcessoRepository PROCESSO_REPOSITORY;
    private final SetorRepository SETOR_REPOSITORY;
    private final UsuarioRepository USUARIO_REPOSITORY;



    public ProcessoResponse criar(ProcessoRequest request){

        Setor setor = SETOR_REPOSITORY.findById(request.setorId()).orElseThrow(SetorNaoEncontradoException::new);
        Usuario usuario = USUARIO_REPOSITORY.findById(request.usuarioId()).orElseThrow(UsuarioNaoEncontradoException::new);

        String protocolo =  gerarProtocolo();

        Processo processo = new Processo(
                protocolo,
                request.titulo(),
                request.descricao(),
                request.prioridade(),
                usuario,
                setor

        );

        PROCESSO_REPOSITORY.save(processo);
        return ProcessoResponse.from(processo);

    }

    @Transactional(readOnly = true)
    public ProcessoResponse buscarPorId(Long id){
        Processo processo = PROCESSO_REPOSITORY.findById(id).orElseThrow(ProcessoNaoEncontradoException::new);

        return ProcessoResponse.from(processo);
    }

    @Transactional(readOnly = true)
     public Page<ProcessoResponse> listarTodos(Pageable pageable){
        return PROCESSO_REPOSITORY.findAll(pageable).map(ProcessoResponse::from);
    }

    @Transactional(readOnly = true)
    public ProcessoResponse buscarPorNumeroProtocolo(String numeroP){
        Processo processo = PROCESSO_REPOSITORY.findByNumeroProtocolo(numeroP).orElseThrow(ProcessoNaoEncontradoException::new);
        return ProcessoResponse.from(processo);
    }

    private String gerarProtocolo(){
        int ano = Year.now().getValue();
        long sequencial = PROCESSO_REPOSITORY.count() + 1;
        return String.format("PROC-%d-05%d", ano, sequencial);
    }
}
