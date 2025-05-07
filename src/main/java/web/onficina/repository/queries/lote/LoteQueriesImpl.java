package web.onficina.repository.queries.lote;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.onficina.filter.LoteFilter;
import web.onficina.model.Lote;
import web.onficina.repository.pagination.PaginacaoUtil;

public class LoteQueriesImpl implements LoteQueries {

	private static final Logger logger = LoggerFactory.getLogger(LoteQueriesImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Lote> pesquisar(LoteFilter filtro, Pageable pageable) {

		StringBuilder queryLotes = new StringBuilder("select distinct l from Lote l inner join fetch l.vacina");
		StringBuilder condicoes = new StringBuilder();

		criarCondicoes(filtro, condicoes);

		queryLotes.append(condicoes);
		PaginacaoUtil.prepararOrdemJPQL(queryLotes, pageable);
		TypedQuery<Lote> typedQuery = em.createQuery(queryLotes.toString(), Lote.class);
		PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
		typedQuery.setHint("hibernate.query.passDistinctThrough", false);

		preencherParametros(filtro, typedQuery);

		List<Lote> lotes = typedQuery.getResultList();

		logger.info("Calculando o total de registros que o filtro retornará.");
		StringBuilder queryTotal = new StringBuilder("select count(l) from Lote l");
		queryTotal.append(condicoes);
		TypedQuery<Long> typedQueryTotal = em.createQuery(queryTotal.toString(), Long.class);

		preencherParametros(filtro, typedQueryTotal);

		long totalLotes = typedQueryTotal.getSingleResult();
		logger.info("O filtro retornará {} registros.", totalLotes);

		Page<Lote> page = new PageImpl<>(lotes, pageable, totalLotes);

		return page;
	}

	private void criarCondicoes(LoteFilter filtro, StringBuilder condicoes) {
		boolean condicao = false;

		if (filtro.getCodigo() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.codigo = :codigo");
			condicao = true;
		}
		if (filtro.getInicioValidade() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.validade >= :inicioValidade");
			condicao = true;
		}
		if (filtro.getFimValidade() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.validade <= :fimValidade");
			condicao = true;
		}
		if (filtro.getMinimoDosesLote() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.nroDosesDoLote >= :minimoDosesLote");
			condicao = true;
		}
		if (filtro.getMaximoDosesLote() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.nroDosesDoLote <= :maximoDosesLote");
			condicao = true;
		}
		if (filtro.getMinimoDosesAtual() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.nroDosesAtual >= :minimoDosesAtual");
			condicao = true;
		}
		if (filtro.getMaximoDosesAtual() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.nroDosesAtual <= :maximoDosesAtual");
			condicao = true;
		}
		if (filtro.getVacina() != null) {
			fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("l.vacina = :vacina");
			condicao = true;
		}
	}

	private void preencherParametros(LoteFilter filtro, TypedQuery<?> typedQuery) {
		if (filtro.getCodigo() != null) {
			typedQuery.setParameter("codigo", filtro.getCodigo());
		}
		if (filtro.getInicioValidade() != null) {
			typedQuery.setParameter("inicioValidade", filtro.getInicioValidade());
		}
		if (filtro.getFimValidade() != null) {
			typedQuery.setParameter("fimValidade", filtro.getFimValidade());
		}
		if (filtro.getMinimoDosesLote() != null) {
			typedQuery.setParameter("minimoDosesLote", filtro.getMinimoDosesLote());
		}
		if (filtro.getMaximoDosesLote() != null) {
			typedQuery.setParameter("maximoDosesLote", filtro.getMaximoDosesLote());
		}
		if (filtro.getMinimoDosesAtual() != null) {
			typedQuery.setParameter("minimoDosesAtual", filtro.getMinimoDosesAtual());
		}
		if (filtro.getMaximoDosesAtual() != null) {
			typedQuery.setParameter("maximoDosesAtual", filtro.getMaximoDosesAtual());
		}
		if (filtro.getVacina() != null) {
			typedQuery.setParameter("vacina", filtro.getVacina());
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
