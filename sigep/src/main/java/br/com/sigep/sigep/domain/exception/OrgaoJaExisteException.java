package br.com.sigep.sigep.domain.exception;

public class OrgaoJaExisteException extends RuntimeException {
    public OrgaoJaExisteException(String message) {
        super(message);
    }
}
