package web.onficina.repository.queries.manutencao;

import java.util.List;

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
        // Usa o alias 'm' para Manutencao
        StringBuilder jpql = new StringBuilder("select m from Manutencao m");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        // --- Adaptação dos Filtros ---
        // Para Enums, verificamos se não são nulos, em vez de usar StringUtils.hasText
        if (filtro.getTipoManutencao() != null) {
            condicoes.append(" and m.tipoManutencao = :tipoManutencao");
        }
        if (filtro.getStatusManutencao() != null) {
            condicoes.append(" and m.statusManutencao = :statusManutencao");
        }
        if (filtro.getTipoServico() != null) {
            condicoes.append(" and m.tipoServico = :tipoServico");
        }
        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            condicoes.append(" and m.veiculo.id = :veiculoId");
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            condicoes.append(" and m.oficina.id = :oficinaId");
        }
        // Adiciona um filtro extra para buscar manutenções de um veículo específico, se
        // necessário
        // if (filtro.getVeiculo() != null) {
        // condicoes.append(" and m.veiculo = :veiculo");
        // }

        jpql.append(condicoes);
        // Prepara a ordenação (ex: ?sort=valorServico,desc)
        PaginacaoUtil.prepararOrdemJPQL(jpql, "m", pageable);

        TypedQuery<Manutencao> query = em.createQuery(jpql.toString(), Manutencao.class);
        // Prepara o intervalo da paginação (quais registros buscar)
        PaginacaoUtil.prepararIntervalo(query, pageable);

        // --- Adaptação dos Parâmetros ---
        // Para Enums, passamos o objeto do enum diretamente, sem '%' ou toLowerCase()
        if (filtro.getTipoManutencao() != null) {
            query.setParameter("tipoManutencao", filtro.getTipoManutencao());
        }
        if (filtro.getStatusManutencao() != null) {
            query.setParameter("statusManutencao", filtro.getStatusManutencao());
        }
        if (filtro.getTipoServico() != null) {
            query.setParameter("tipoServico", filtro.getTipoServico());
        }
        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            query.setParameter("veiculoId", filtro.getVeiculoId());
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            query.setParameter("oficinaId", filtro.getOficinaId());
        }

        List<Manutencao> resultado = query.getResultList();

        // --- Query para Contagem Total de Registros (para a paginação) ---
        StringBuilder jpqlCount = new StringBuilder("select count(m) from Manutencao m");
        jpqlCount.append(condicoes);
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        // Define os mesmos parâmetros para a query de contagem
        if (filtro.getTipoManutencao() != null) {
            countQuery.setParameter("tipoManutencao", filtro.getTipoManutencao());
        }
        if (filtro.getStatusManutencao() != null) {
            countQuery.setParameter("statusManutencao", filtro.getStatusManutencao());
        }
        if (filtro.getTipoServico() != null) {
            countQuery.setParameter("tipoServico", filtro.getTipoServico());
        }
        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            countQuery.setParameter("veiculoId", filtro.getVeiculoId());
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            countQuery.setParameter("oficinaId", filtro.getOficinaId());
        }
        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }

}