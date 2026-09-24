package br.com.sigep.sigep.presentation.controller;


import br.com.sigep.sigep.application.dto.processo.ProcessoRequest;
import br.com.sigep.sigep.application.dto.processo.ProcessoResponse;
import br.com.sigep.sigep.application.dto.processo.TramitacaoProcessoRequest;
import br.com.sigep.sigep.application.service.ProcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/processos")
@RequiredArgsConstructor
public class ProcessoController {

    private final ProcessoService PROCESSO_SERVICE;

    @PostMapping
    public ResponseEntity<ProcessoResponse> criar(@Valid @RequestBody ProcessoRequest request, UriComponentsBuilder uriBuilder){
        ProcessoResponse response = PROCESSO_SERVICE.criar(request);
        URI uri = uriBuilder.path("api/v1/processo/{id}")
                .buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProcessoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(PROCESSO_SERVICE.buscarPorId(id));
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<ProcessoResponse> buscarPorNumeroProtocolo(@PathVariable String protocolo){
        return ResponseEntity.ok(PROCESSO_SERVICE.buscarPorNumeroProtocolo(protocolo));
    }

    @PatchMapping("/{id}/tramitar")
    public ResponseEntity<ProcessoResponse> tramitar(@PathVariable Long id, TramitacaoProcessoRequest request){

        return ResponseEntity.ok(PROCESSO_SERVICE.tramitar(id, request));
    }
}
