package br.com.sigep.sigep.presentation.controller;


import br.com.sigep.sigep.application.dto.orgao.OrgaoRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoResponse;
import br.com.sigep.sigep.application.service.OrgaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orgao")
@RequiredArgsConstructor
public class OrgaoController {

    private final OrgaoService ORGAO_SERVICE;


    @PostMapping
    public ResponseEntity<OrgaoResponse> cadastrar(@Valid @RequestBody OrgaoRequest orgaoRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ORGAO_SERVICE.cadastrar(orgaoRequest));
    }
}
