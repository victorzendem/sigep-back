package br.com.sigep.sigep.presentation.controller;


import br.com.sigep.sigep.application.dto.orgao.OrgaoPatchRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoRequest;
import br.com.sigep.sigep.application.dto.orgao.OrgaoResponse;
import br.com.sigep.sigep.application.dto.orgao.OrgaoUpdateRequest;
import br.com.sigep.sigep.application.service.OrgaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<OrgaoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody OrgaoUpdateRequest request){
        return ResponseEntity.ok(ORGAO_SERVICE.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrgaoResponse> atualizarParcialmente(@PathVariable Long id, @Valid @RequestBody OrgaoPatchRequest request){
        return ResponseEntity.ok(ORGAO_SERVICE.atualizarParciamente(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<OrgaoResponse>> listarTodos(Pageable pageable){
        return ResponseEntity.ok(ORGAO_SERVICE.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgaoResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(ORGAO_SERVICE.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        ORGAO_SERVICE.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
