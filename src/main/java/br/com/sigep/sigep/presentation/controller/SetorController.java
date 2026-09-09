package br.com.sigep.sigep.presentation.controller;

import br.com.sigep.sigep.application.dto.setor.SetorRequest;
import br.com.sigep.sigep.application.dto.setor.SetorResponse;
import br.com.sigep.sigep.application.dto.setor.SetorUpdateRequest;
import br.com.sigep.sigep.application.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/setor")
public class SetorController {


    private final SetorService SETOR_SERVICE;

    @PostMapping
    public ResponseEntity<SetorResponse> cadastrar(SetorRequest setorRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SETOR_SERVICE.cadastrar(setorRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorResponse> atualizar(@PathVariable Long id, @Valid @RequestBody SetorUpdateRequest request){
        return ResponseEntity.ok(SETOR_SERVICE.atualizar(id, request));
    }
}
