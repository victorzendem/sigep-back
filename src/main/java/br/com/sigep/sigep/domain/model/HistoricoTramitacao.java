package br.com.sigep.sigep.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_tramitacao")
@NoArgsConstructor
@Data
public class HistoricoTramitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_origem_id", nullable = false)
    private Setor setorOrigem;


    @ManyToOne(fetch  = FetchType.LAZY)
    @JoinColumn(name = "setor_destino_id", nullable = false)
    private Setor setorDestino;


    @Column(name = "despacho", length = 150, nullable = false)
    private String despacho;

    @Column(name = "data_tramitacao", nullable = false, updatable = false)
    private LocalDateTime dataTramitacao;


    public HistoricoTramitacao(Processo processo, Setor setorOrigem, Setor setorDestino, String despacho) {
        this.processo = processo;
        this.setorOrigem = setorOrigem;
        this.setorDestino = setorDestino;
        this.despacho = despacho;
        this.dataTramitacao = LocalDateTime.now();
    }
}
