package br.com.sigep.sigep.domain.model;

import br.com.sigep.sigep.domain.enums.PrioridadeProcesso;
import br.com.sigep.sigep.domain.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "processos")
@NoArgsConstructor
@Data
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_protocolo", length = 30, unique = true, nullable = false)
    private String numeroProtocolo;

    @Column(name = "titulo", length = 200)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_processo", nullable = false)
    private StatusProcesso statusProcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade_processo", nullable = false)
    private PrioridadeProcesso prioridadeProcesso;


    @CreationTimestamp
    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "setor_atual_id", nullable = false)
    private Setor setorAtual;


    public Processo(String numero, String assunto, String descricao , PrioridadeProcesso prioridadeProcesso, Usuario responsavel, Setor setorAtual) {
        this.numeroProtocolo = numero;
        this.titulo = assunto;
        this.descricao = descricao;
        this.statusProcesso = StatusProcesso.ABERTO;
        this.prioridadeProcesso = prioridadeProcesso;
        this.dataAbertura = LocalDateTime.now();
        this.responsavel = responsavel;
        this.setorAtual = setorAtual;
    }


    public void tramitar(Setor novoSetorId){

        if(this.statusProcesso == StatusProcesso.CONCLUIDO || this.statusProcesso == StatusProcesso.CANCELADO){
            throw new IllegalStateException("Não é possível tramitar um processo canecelado ou concluído.");
        }

        if(this.setorAtual.getId().equals(novoSetorId.getId())){
            throw new  IllegalArgumentException("O processo já se encontra no destino informado.");
        }

        this.setorAtual = novoSetorId;
        this.statusProcesso =  StatusProcesso.EM_ANDAMENTO;
    }
}
