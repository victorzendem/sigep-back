package br.com.sigep.sigep.application.service;


import br.com.sigep.sigep.domain.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secret;

    //gerar o token com assinatura simetrica(a mesma chave que assina, tambem valida.
    //usa o construtor da biblioteca JWT pra fazer a criacao do token
    public String gerarToken(Usuario usuario){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    . withIssuer("sigep-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withClaim("perfil", usuario.getPerfilUsuario().name())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);

        }
        catch(JWTCreationException ex){
            throw new RuntimeException("Erro ao gerar token: ", ex);
        }
    }


    // recebe o token da requisicao, verifica se é válido e se não tá expirado, no final, devolve o email do usuário.
    // Do contrário, retona a excecao.
    public String validarToken(String tokenJWT){
        Algorithm algoritmo = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algoritmo)
                    .withIssuer("sigep-api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch(JWTVerificationException ex){
            return null;
        }
    }

    //define o tempo de expiracao do token JWT, nessa caso, duas horas contando a partir do horario atual e no horario de brasilia.
    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
