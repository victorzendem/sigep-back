package br.com.sigep.sigep.model;

import java.time.LocalDate;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private boolean ativo;
    LocalDate dataCriaca;
    LocalDate dataAtualizacao;
}
