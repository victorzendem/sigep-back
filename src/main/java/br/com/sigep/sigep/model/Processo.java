package br.com.sigep.sigep.model;

import br.com.sigep.sigep.enums.PrioridadeProcesso;
import br.com.sigep.sigep.enums.StatusProcesso;

import java.time.LocalDate;

public class Processo {


    private String numero;
    private String assunto;
    private String descricao;
    private StatusProcesso statusProcesso;
    private PrioridadeProcesso prioridadeProcesso;
    private LocalDate dataAbertura;
    private LocalDate prazo;
    private Usuario responsavel;
    private Setor setorAtual;
}
