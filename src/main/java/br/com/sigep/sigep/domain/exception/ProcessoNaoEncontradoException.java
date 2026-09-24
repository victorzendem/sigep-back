package br.com.sigep.sigep.domain.exception;

public class ProcessoNaoEncontradoException extends RuntimeException {
    public ProcessoNaoEncontradoException(String message) {
        super(message);
    }
    public ProcessoNaoEncontradoException(){
        super("Processo não encontrado.");
    }
}
