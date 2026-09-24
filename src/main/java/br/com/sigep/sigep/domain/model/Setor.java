package br.com.sigep.sigep.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "setores",
uniqueConstraints = {
        @UniqueConstraint(name = "uk_setor_orgao_sigla", columnNames ={"orgao_id", "sigla"}),
        @UniqueConstraint(name = "uk_setor_orgao_nome", columnNames = {"orgao_id", "nome"})
})
@NoArgsConstructor
@Data
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 40, nullable = false)
    private String nome;

    @Column(name = "sigla", length = 15, nullable = false)
    private String sigla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgao_id", nullable = false)
    private Orgao orgao;

    @Column(nullable = false)
    private boolean ativo = true;


    public Setor(String nome, String sigla, Orgao orgao){
        this.nome = nome;
        this.sigla = sigla;
        this.orgao = orgao;
    }


    public void atualizar(String nome, String sigla){
        this.nome = nome;
        this.sigla = sigla;
    }

    public void atualizarParcialmente(String nome, String sigla){
        if(nome != null){
            this.nome = nome;
        }
        if(sigla != null){
            this.sigla = sigla;
        }
    }
}
