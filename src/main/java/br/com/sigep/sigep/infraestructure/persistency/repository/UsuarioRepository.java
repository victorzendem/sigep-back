package br.com.sigep.sigep.infraestructure.persistency.repository;

import br.com.sigep.sigep.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
