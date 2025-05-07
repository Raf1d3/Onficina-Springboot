package web.onficina.repository.queries.vacina;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.onficina.filter.VacinaFilter;
import web.onficina.model.Vacina;
import web.onficina.repository.pagination.PaginacaoUtil;

public class VacinaQueriesImpl implements VacinaQueries {

	private static final Logger logger = LoggerFactory.getLogger(VacinaQueriesImpl.class);

	@PersistenceContext
	private EntityManager em;

	public List<Vacina> pesquisar(VacinaFilter filtro) {
		StringBuilder queryVacinas = new StringBuilder("select distinct v from Vacina v");
		StringBuilder condicoes = new StringBuilder();

		criarCondicoes(filtro, condicoes);
		if (condicoes.toString().isEmpty()) {
			condicoes.append(" where ");
		} else {
			condicoes.append(" and ");
		}
		condicoes.append("v.status = 'ATIVO'");

		queryVacinas.append(condicoes);
		TypedQuery<Vacina> typedQuery = em.createQuery(queryVacinas.toString(), Vacina.class);
		typedQuery.setHint("hibernate.query.passDistinctThrough", false);

		preencherParametros(filtro, typedQuery);

		List<Vacina> vacinas = typedQuery.getResultList();

		return vacinas;
	}

	public Page<Vacina> pesquisar(VacinaFilter filtro, Pageable pageable) {

		StringBuilder queryVacinas = new StringBuilder("select distinct v from Vacina v");
		StringBuilder condicoes = new StringBuilder();

		criarCondicoes(filtro, condicoes);
		if (condicoes.toString().isEmpty()) {
			condicoes.append(" where ");
		} else {
			condicoes.append(" and ");
		}
		condicoes.append("v.status = 'ATIVO'");

		queryVacinas.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryVacinas, pageable);
		TypedQuery<Vacina> typedQuery = em.createQuery(queryVacinas.toString(), Vacina.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		typedQuery.setHint("hibernate.query.passDistinctThrough", false);

		preencherParametros(filtro, typedQuery);

		List<Vacina> vacinas = typedQuery.getResultList();

		logger.info("Calculando o total de registros que o filtro retornará.");
		StringBuilder queryTotal = new StringBuilder("select count(v) from Vacina v");
		queryTotal.append(condicoes);
		TypedQuery<Long> typedQueryTotal = em.createQuery(queryTotal.toString(), Long.class);

		preencherParametros(filtro, typedQueryTotal);

		long totalVacinas = typedQueryTotal.getSingleResult();
		logger.info("O filtro retornará {} registros.", totalVacinas);

		Page<Vacina> page = new PageImpl<>(vacinas, pageable, totalVacinas);

		return page;
	}

	private void criarCondicoes(VacinaFilter filtro, StringBuilder condicoes) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("v.codigo = :codigo");
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getNome())) {
			fazerLigacaoCondicoes(condicoes, condicao);		
			condicoes.append("lower(v.nome) like :nome");
			condicao = true;
		}
		if (StringUtils.hasText(filtro.getDescricao())) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("lower(v.descricao) like :descricao");
			condicao = true;
		}
	}

	private void preencherParametros(VacinaFilter filtro, TypedQuery<?> typedQuery) {
		if (filtro.getCodigo() != null) {
			typedQuery.setParameter("codigo", filtro.getCodigo());
		}
		if (StringUtils.hasText(filtro.getNome())) {
			typedQuery.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
		}
		if (StringUtils.hasText(filtro.getDescricao())) {
			typedQuery.setParameter("descricao", "%" + filtro.getDescricao().toLowerCase() + "%");
		}
	}

	private void fazerLigacaoCondicoes(StringBuilder condicoes, boolean condicao) {
		if (!condicao) {
			condicoes.append(" where ");
		} else {
			condicoes.append(" and ");
		}
	}

}
