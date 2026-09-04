package br.com.sigep.sigep.infraestructure.persistency.repository;

import br.com.sigep.sigep.domain.model.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {
}
