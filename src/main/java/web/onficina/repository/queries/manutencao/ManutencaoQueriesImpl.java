package web.onficina.repository.queries.manutencao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import web.onficina.filter.ManutencaoFilter;
import web.onficina.model.Manutencao;
import web.onficina.pagination.PaginacaoUtil;

public class ManutencaoQueriesImpl implements ManutencaoQueries {

    private static final Logger logger = LoggerFactory.getLogger(ManutencaoQueriesImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Manutencao> pesquisar(ManutencaoFilter filtro, Pageable pageable) {
        StringBuilder queryManutencao = new StringBuilder("select distinct m from Manutencao m");
        StringBuilder condicoes = new StringBuilder();

        Map<String, Object> parametros = new HashMap<>();

        preencherCondicoesEParametros(filtro, condicoes, parametros);

        if (condicoes.isEmpty()) {
            condicoes.append(" where m.status = 'ATIVO'");
        } else {
            condicoes.append(" and m.status = 'ATIVO'");
        }

        queryManutencao.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryManutencao, "m", pageable);
        TypedQuery<Manutencao> typedQuery = em.createQuery(queryManutencao.toString(), Manutencao.class);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        PaginacaoUtil.preencherParametros(parametros, typedQuery);
        List<Manutencao> Manutencao = typedQuery.getResultList();

        long totalManutencao = PaginacaoUtil.getTotalRegistros("Manutencao", "m", condicoes, parametros, em);

        return new PageImpl<>(Manutencao, pageable, totalManutencao);
    }

    private void preencherCondicoesEParametros(ManutencaoFilter filtro, StringBuilder condicoes,
            Map<String, Object> parametros) {
        boolean condicao = false;

        if (filtro.getId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.id = :id");
            parametros.put("id", filtro.getId());
            condicao = true;
        }
        if (filtro.getOficinaId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.oficina.id = :oficinaId");
            parametros.put("oficinaId", filtro.getOficinaId());
            condicao = true;
        }
        if (filtro.getVeiculoId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.veiculo.id = :veiculoId");
            parametros.put("veiculoId", filtro.getVeiculoId());
            condicao = true;
        }

        if (filtro.getStatusManutencao() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.statusManutencao = :statusManutencao");
            parametros.put("statusManutencao", filtro.getStatusManutencao());
            condicao = true;
        }

        if (filtro.getTipoManutencao() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.tipoManutencao = :tipoManutencao");
            parametros.put("tipoManutencao", filtro.getTipoManutencao());
            condicao = true;
        }

        if (filtro.getTipoServico() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.tipoServico = :tipoServico");
            parametros.put("tipoServico", filtro.getTipoServico());
            condicao = true;
        }

        if (filtro.getValorServico() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("m.valorServico = :valorServico");
            parametros.put("valorServico", filtro.getValorServico());
            condicao = true;
        }
    }
}
