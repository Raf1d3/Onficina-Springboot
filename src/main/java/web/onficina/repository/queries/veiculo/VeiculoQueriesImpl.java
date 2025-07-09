package web.onficina.repository.queries.veiculo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.onficina.filter.VeiculoFilter;
import web.onficina.model.Veiculo;
import web.onficina.pagination.PaginacaoUtil;

public class VeiculoQueriesImpl implements VeiculoQueries {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Veiculo> pesquisar(VeiculoFilter filtro, Pageable pageable) {
		StringBuilder queryVeiculos = new StringBuilder("select distinct v from Veiculo v");
		StringBuilder condicoes = new StringBuilder();

		Map<String, Object> parametros = new HashMap<>();

		preencherCondicoesEParametros(filtro, condicoes, parametros);

		if (condicoes.isEmpty()) {
			condicoes.append(" where v.status = 'ATIVO'");
		} else {
			condicoes.append(" and v.status = 'ATIVO'");
		}

		queryVeiculos.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryVeiculos, "v", pageable);
		TypedQuery<Veiculo> typedQuery = em.createQuery(queryVeiculos.toString(), Veiculo.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		PaginacaoUtil.preencherParametros(parametros, typedQuery);
		List<Veiculo> veiculos = typedQuery.getResultList();

		long totalVeiculos = PaginacaoUtil.getTotalRegistros("Veiculo", "v", condicoes, parametros, em);

		return new PageImpl<>(veiculos, pageable, totalVeiculos);
	}

	private void preencherCondicoesEParametros(VeiculoFilter filtro, StringBuilder condicoes,
			Map<String, Object> parametros) {
		boolean condicao = false;

		if (filtro.getId() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("v.id = :id");
			parametros.put("id", filtro.getId());
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getMarca())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("lower(v.marca) like :marca");
			parametros.put("marca", "%" + filtro.getMarca().toLowerCase() + "%");
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getModelo())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("v.modelo like :modelo");
			parametros.put("modelo", "%" + filtro.getModelo() + "%");
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getPlaca())) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("v.placa like :placa");
			parametros.put("placa", "%" + filtro.getPlaca() + "%");
			condicao = true;
		}
		if (filtro.getAno() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("v.ano = :ano");
			parametros.put("ano", filtro.getAno());
			condicao = true;
		}
	}
}