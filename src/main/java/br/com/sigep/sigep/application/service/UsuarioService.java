package br.com.sigep.sigep.application.service;


import br.com.sigep.sigep.application.dto.usuario.UsuarioRequest;
import br.com.sigep.sigep.application.dto.usuario.UsuarioResponse;
import br.com.sigep.sigep.application.dto.usuario.UsuarioUpdateRequest;
import br.com.sigep.sigep.domain.exception.UsuarioNaoEncontradoException;
import br.com.sigep.sigep.domain.model.Usuario;
import br.com.sigep.sigep.infraestructure.persistency.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository USUARIO_REPOSITORY;

    public UsuarioResponse cadastrar(UsuarioRequest usuarioRequest){

        Usuario usuario = new Usuario(
                usuarioRequest.nome(),
                usuarioRequest.email(),
                usuarioRequest.cpf(),
                usuarioRequest.senha()
        );
        USUARIO_REPOSITORY.save(usuario);

        return UsuarioResponse.from(usuario);
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioUpdateRequest request){

        Usuario usuario = USUARIO_REPOSITORY.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
        usuario.atualizar(
                request.nome(),
                request.email(),
                request.cpf(),
                request.senha()
        );

        return UsuarioResponse.from(usuario);
    }
}
