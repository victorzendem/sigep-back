package br.com.sigep.sigep.presentation.exception;

import br.com.sigep.sigep.domain.exception.OrgaoNaoEncontradoException;
import br.com.sigep.sigep.domain.exception.SetorJaExisteException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandle {

    @ExceptionHandler(SetorJaExisteException.class)
    public ResponseEntity<ErrorResponse> handleSetorJaExiste(SetorJaExisteException ex){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Operacão não permitida: violacão de restricão de integridade no banco de dados. ",
                LocalDateTime.now(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
