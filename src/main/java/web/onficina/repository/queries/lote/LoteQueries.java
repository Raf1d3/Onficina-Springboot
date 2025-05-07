package web.onficina.repository.queries.lote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.LoteFilter;
import web.onficina.model.Lote;

public interface LoteQueries {

	Page<Lote> pesquisar(LoteFilter filtro, Pageable pageable);
	
}
