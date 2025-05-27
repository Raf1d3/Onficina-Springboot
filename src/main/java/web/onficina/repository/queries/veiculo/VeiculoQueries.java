package web.onficina.repository.queries.veiculo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import web.onficina.filter.VeiculoFilter;
import web.onficina.model.Veiculo;

public interface VeiculoQueries {
    Page<Veiculo> pesquisar(VeiculoFilter filtro, Pageable pageable);
}
