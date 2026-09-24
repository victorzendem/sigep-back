package br.com.sigep.sigep.infraestructure.persistency.repository;

import br.com.sigep.sigep.domain.model.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    Page<Setor> findByAtivoTrue(Pageable pageable);

    boolean existsBySiglaAndOrgaoId(String sigla, Long orgaoId);
    boolean existsByNomeAndOrgaoId(String nome, Long orgaoId);

    boolean existsBySiglaAndOrgaoIdAndIdNot(String sigla, Long orgaoId, Long id);
    boolean existsByNomeAndOrgaoIdAndIdNot(String nome, Long orgaoId, Long id);
}
