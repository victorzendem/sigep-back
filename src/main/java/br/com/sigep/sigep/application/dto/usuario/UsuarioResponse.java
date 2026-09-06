package br.com.sigep.sigep.application.dto.usuario;

import br.com.sigep.sigep.domain.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        boolean ativo
) {

    public static UsuarioResponse from(Usuario usuario){
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.isAtivo());
    }
}
