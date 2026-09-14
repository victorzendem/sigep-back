package br.com.sigep.sigep.application.dto.processo;

import br.com.sigep.sigep.domain.enums.PrioridadeProcesso;
import br.com.sigep.sigep.domain.enums.StatusProcesso;
import br.com.sigep.sigep.domain.model.Processo;

import java.time.LocalDateTime;

public record ProcessoResponse(
        Long id,
        String numeroProtocolo,
        String titulo,
        String descricao,
        StatusProcesso status,
        PrioridadeProcesso prioridade,
        Long responsavelId,
        String nomeResponsavel,
        Long setorId,
        String nomeSetor,
        LocalDateTime dataAbertura

) {

    public static ProcessoResponse from(Processo processo){
        return new ProcessoResponse(
                processo.getId(),
                processo.getNumeroProtocolo(),
                processo.getTitulo(),
                processo.getDescricao(),
                processo.getStatusProcesso(),
                processo.getPrioridadeProcesso(),
                processo.getResponsavel().getId(),
                processo.getResponsavel().getNome(),
                processo.getSetorAtual().getId(),
                processo.getSetorAtual().getNome(),
                processo.getDataAbertura()
        );
    }
}
