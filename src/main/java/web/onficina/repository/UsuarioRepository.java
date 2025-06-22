package web.onficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmailIgnoreCase(String email);
    
}
