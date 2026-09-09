package br.com.sigep.sigep.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException
{
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(){
        super("Usuário não encontrado.");
    }
}
