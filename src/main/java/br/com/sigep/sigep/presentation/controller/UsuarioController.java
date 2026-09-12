package br.com.sigep.sigep.presentation.controller;


import br.com.sigep.sigep.application.dto.usuario.UsuarioPatchRequest;
import br.com.sigep.sigep.application.dto.usuario.UsuarioRequest;
import br.com.sigep.sigep.application.dto.usuario.UsuarioResponse;
import br.com.sigep.sigep.application.dto.usuario.UsuarioUpdateRequest;
import br.com.sigep.sigep.application.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService USUARIO_SERVICE;


    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest usuarioRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(USUARIO_SERVICE.cadastrar(usuarioRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest request){
        return ResponseEntity.ok(USUARIO_SERVICE.atualizar(id,request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarParcialmente(@PathVariable Long id, @Valid @RequestBody UsuarioPatchRequest request){
        return ResponseEntity.ok(USUARIO_SERVICE.atualizarParcialmente(id, request));
    }

}
