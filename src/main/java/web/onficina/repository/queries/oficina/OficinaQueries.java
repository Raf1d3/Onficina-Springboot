package web.onficina.repository.queries.oficina;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.OficinaFilter;
import web.onficina.model.Oficina;

public interface OficinaQueries {
    Page<Oficina> pesquisar(OficinaFilter filtro, Pageable pageable);
}
