package br.com.sigep.sigep.presentation.exception;

import br.com.sigep.sigep.domain.exception.OrgaoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandle {

    @ExceptionHandler(OrgaoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleOrgaoNaoEncontrado(OrgaoNaoEncontradoException exception) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now(),
                List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
