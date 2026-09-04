package br.com.sigep.sigep.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orgao")
@Data
@NoArgsConstructor
public class Orgao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 40)
    private String nome;

    @Column(name = "sigla", nullable = false, length = 20)
    private String sigla;

    @Column(name = "cnpj", unique = true, length = 20, nullable = false)
    private String cnpj;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    public Orgao(String nome, String sigla, String cnpj){
        this.nome = nome;
        this.sigla = sigla;
        this.cnpj = cnpj;
    }
}
