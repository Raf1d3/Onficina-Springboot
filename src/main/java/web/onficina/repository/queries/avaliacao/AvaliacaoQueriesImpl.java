package web.onficina.repository.queries.avaliacao;

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
import web.onficina.filter.AvaliacaoFilter;
import web.onficina.model.Avaliacao;
import web.onficina.pagination.PaginacaoUtil;

public class AvaliacaoQueriesImpl implements AvaliacaoQueries {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Avaliacao> pesquisar(AvaliacaoFilter filtro, Pageable pageable) {
        StringBuilder queryAvaliacao = new StringBuilder("select distinct a from Avaliacao a");
        StringBuilder condicoes = new StringBuilder();

        Map<String, Object> parametros = new HashMap<>();

        preencherCondicoesEParametros(filtro, condicoes, parametros);

        if (condicoes.isEmpty()) {
            condicoes.append(" where a.status = 'ATIVO'");
        } else {
            condicoes.append(" and a.status = 'ATIVO'");
        }

        queryAvaliacao.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryAvaliacao, "a", pageable);
        TypedQuery<Avaliacao> typedQuery = em.createQuery(queryAvaliacao.toString(), Avaliacao.class);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        PaginacaoUtil.preencherParametros(parametros, typedQuery);
        List<Avaliacao> avaliacao = typedQuery.getResultList();

        long totalAvaliacao = PaginacaoUtil.getTotalRegistros("Avaliacao", "a", condicoes, parametros, em);

        return new PageImpl<>(avaliacao, pageable, totalAvaliacao);
    }

    private void preencherCondicoesEParametros(AvaliacaoFilter filtro, StringBuilder condicoes,
            Map<String, Object> parametros) {
        boolean condicao = false;

        if (filtro.getId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.id = :id");
            parametros.put("id", filtro.getId());
            condicao = true;
        }

        if (filtro.getOficinaId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.oficina.id = :oficinaId");
            parametros.put("oficinaId", filtro.getOficinaId());
            condicao = true;
        }
        if (filtro.getVeiculoId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.manutencao.veiculo.id = :veiculoId");
            parametros.put("veiculoId", filtro.getVeiculoId());
            condicao = true;
        }
        if (filtro.getManutencaoId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.manutencao.id = :manutencaoId");
            parametros.put("manutencaoId", filtro.getManutencaoId());
            condicao = true;
        }
        if (filtro.getProprietarioId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.manutencao.veiculo.proprietario.id = :proprietarioId");
            parametros.put("proprietarioId", filtro.getProprietarioId());
            condicao = true;
        }
        if (filtro.getNota() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("a.nota = :nota");
            parametros.put("nota", filtro.getNota());
            condicao = true;
        }

        if (StringUtils.hasText(filtro.getComentario())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("lower(a.comentario) like :comentario");
            parametros.put("comentario", "%" + filtro.getComentario().toLowerCase() + "%");
            condicao = true;
        }

        if (filtro.getDataAvaliacao() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("a.dataAvaliacao >= :dataAvaliacao");
			parametros.put("dataAvaliacao", filtro.getDataAvaliacao());
			condicao = true;
		}
    }
}
