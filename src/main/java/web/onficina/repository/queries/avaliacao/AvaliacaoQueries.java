package web.onficina.repository.queries.avaliacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.AvaliacaoFilter;
import web.onficina.model.Avaliacao;

public interface AvaliacaoQueries {
    Page<Avaliacao> pesquisar(AvaliacaoFilter filtro, Pageable pageable);
}
