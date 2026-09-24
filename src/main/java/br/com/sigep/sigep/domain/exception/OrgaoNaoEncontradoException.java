package br.com.sigep.sigep.domain.exception;

public class OrgaoNaoEncontradoException extends RuntimeException {
    public OrgaoNaoEncontradoException(String message) {
        super(message);
    }

    public OrgaoNaoEncontradoException(){
        super("Orgão não encontrado.");
    }
}

