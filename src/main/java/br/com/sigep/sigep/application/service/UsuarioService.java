package br.com.sigep.sigep.application.service;


import br.com.sigep.sigep.application.dto.usuario.UsuarioRequest;
import br.com.sigep.sigep.application.dto.usuario.UsuarioResponse;
import br.com.sigep.sigep.domain.model.Usuario;
import br.com.sigep.sigep.infraestructure.persistency.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
