package br.com.sigep.sigep.infraestructure.security;

import br.com.sigep.sigep.infraestructure.persistency.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository USUARIO_REPOSITORY;


    //o spring secutiry precisa das informacoes do usuario quando ele tentar logar, esta classe vai fazer a busca
    // no banco de dados.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = USUARIO_REPOSITORY.findByEmail(username);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }
        return usuario;
    }
}
