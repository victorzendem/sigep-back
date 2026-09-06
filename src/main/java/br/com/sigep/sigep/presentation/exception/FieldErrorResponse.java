package br.com.sigep.sigep.presentation.exception;

public record FieldErrorResponse(
    String campo,
    String mensagem
) {
}
