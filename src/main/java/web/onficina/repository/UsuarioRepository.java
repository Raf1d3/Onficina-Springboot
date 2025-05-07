package web.onficina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.modelOnficina.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    
}
