package web.onficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Usuario;
import web.onficina.repository.queries.usuario.UsuarioQueries;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQueries{

    Usuario findByEmailAndAtivo(String email, Boolean ativo);
    Usuario findByEmail(String email);
    Usuario findByIdAndAtivo(Long id, Boolean ativo);
    Usuario findAllByAtivo(Boolean ativo);
}
