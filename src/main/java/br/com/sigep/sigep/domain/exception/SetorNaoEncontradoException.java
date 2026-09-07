package br.com.sigep.sigep.domain.exception;

public class SetorNaoEncontradoException extends RuntimeException {
    public SetorNaoEncontradoException(String message) {
        super(message);
    }
}
