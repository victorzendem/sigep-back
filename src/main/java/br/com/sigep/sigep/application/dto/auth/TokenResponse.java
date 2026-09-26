package br.com.sigep.sigep.application.dto.auth;

public record TokenResponse(
        String token,
        String tipo
) {

    public TokenResponse(String token){
        this(token, "Bearer");
    }
}
