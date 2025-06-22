package web.onficina.repository.queries.manutencao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.ManutencaoFilter;
import web.onficina.model.Manutencao;

public interface ManutencaoQueries {
    Page<Manutencao> pesquisar(ManutencaoFilter filtro, Pageable pageable);
}
