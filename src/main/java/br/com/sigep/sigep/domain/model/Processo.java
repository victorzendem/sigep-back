package br.com.sigep.sigep.domain.model;

import br.com.sigep.sigep.enums.PrioridadeProcesso;
import br.com.sigep.sigep.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "processos")
@NoArgsConstructor
@Data
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", length = 20, unique = true, nullable = false)
    private String numero;

    @Column(name = "assunto", length = 200)
    private String assunto;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_processo", nullable =  false)
    private StatusProcesso statusProcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade_processo", nullable = false)
    private PrioridadeProcesso prioridadeProcesso;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Column(name = "prazo", nullable = false)
    private LocalDate prazo;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "setor_atual_id", nullable = false)
    private Setor setorAtual;


    public Processo(String numero, String assunto, String descricao, StatusProcesso statusProcesso, PrioridadeProcesso prioridadeProcesso, LocalDate dataAbertura, LocalDate prazo, Usuario responsavel, Setor setorAtual) {
        this.numero = numero;
        this.assunto = assunto;
        this.descricao = descricao;
        this.statusProcesso = statusProcesso;
        this.prioridadeProcesso = prioridadeProcesso;
        this.dataAbertura = dataAbertura;
        this.prazo = prazo;
        this.responsavel = responsavel;
        this.setorAtual = setorAtual;
    }
}
