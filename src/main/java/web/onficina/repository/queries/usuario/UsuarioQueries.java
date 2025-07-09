package web.onficina.repository.queries.usuario;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.UsuarioFilter;
import web.onficina.model.Usuario;


public interface UsuarioQueries {
    Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable);
}
