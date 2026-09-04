package br.com.sigep.sigep.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;


@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Email
    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "senha",nullable = false)
    private String senha;

    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;


    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    LocalDate dataCriacao;

    @CreationTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    LocalDate dataAtualizacao;

    public Usuario(String nome, String email, String senha, String cpf, boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.ativo = ativo;
    }
}
