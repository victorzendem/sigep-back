package br.com.sigep.sigep.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        int status,
        String mensagem,
        LocalDateTime timestamp,
        List<FieldErrorResponse> errors

) {
}
