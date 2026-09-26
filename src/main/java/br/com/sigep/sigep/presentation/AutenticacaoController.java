package br.com.sigep.sigep.presentation;


import br.com.sigep.sigep.application.dto.auth.LoginRequest;
import br.com.sigep.sigep.application.dto.auth.TokenResponse;
import br.com.sigep.sigep.application.service.TokenService;
import br.com.sigep.sigep.domain.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager AUTHENTICATION_MANAGER;
    private final TokenService TOKEN_SERVICE;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request){
        var dadosUsuario = new UsernamePasswordAuthenticationToken(request.email(), request.senha());

        Authentication authentication = AUTHENTICATION_MANAGER.authenticate(dadosUsuario);

        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

        String tokenJWT = TOKEN_SERVICE.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }
}
