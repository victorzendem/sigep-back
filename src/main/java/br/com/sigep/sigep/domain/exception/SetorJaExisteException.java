package br.com.sigep.sigep.domain.exception;

public class SetorJaExisteException extends RuntimeException {
    public SetorJaExisteException(String message) {
        super(message);
    }
}
