package web.onficina.repository.queries.vacina;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.onficina.filter.VacinaFilter;
import web.onficina.model.Vacina;

public interface VacinaQueries {

	List<Vacina> pesquisar(VacinaFilter filtro);
	Page<Vacina> pesquisar(VacinaFilter filtro, Pageable pageable);
	
}
