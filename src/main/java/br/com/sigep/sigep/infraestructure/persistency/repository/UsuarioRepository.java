package br.com.sigep.sigep.infraestructure.persistency.repository;

import br.com.sigep.sigep.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByAtivoTrue(Pageable pageable);

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
