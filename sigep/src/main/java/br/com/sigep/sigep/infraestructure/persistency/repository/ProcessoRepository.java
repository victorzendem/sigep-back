package br.com.sigep.sigep.infraestructure.persistency.repository;

import br.com.sigep.sigep.domain.model.Processo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    Optional<Processo> findByNumeroProtocolo(String numeroProtocolo);

     boolean existsByNumeroProtocolo(String numeroProtocolo);

     long count();

}
