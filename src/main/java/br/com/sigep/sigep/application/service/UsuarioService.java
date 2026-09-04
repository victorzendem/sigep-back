package br.com.sigep.sigep.application.service;


import br.com.sigep.sigep.infraestructure.persistency.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository USUARIO_REPOSITORY;
}
