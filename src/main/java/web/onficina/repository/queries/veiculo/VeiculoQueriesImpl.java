package web.onficina.repository.queries.veiculo;

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
import web.onficina.filter.VeiculoFilter;
import web.onficina.model.Veiculo;
import web.onficina.pagination.PaginacaoUtil;

public class VeiculoQueriesImpl implements VeiculoQueries {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoQueriesImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Veiculo> pesquisar(VeiculoFilter filtro, Pageable pageable) {
        StringBuilder queryVeiculos = new StringBuilder("select v from Veiculo v");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        if (StringUtils.hasText(filtro.getPlaca())) {
            condicoes.append(" and lower(v.placa) like :placa");
        }

        if (StringUtils.hasText(filtro.getModelo())) {
            condicoes.append(" and lower(v.modelo) like :modelo");
        }

        if (StringUtils.hasText(filtro.getMarca())) {
            condicoes.append(" and lower(v.marca) like :marca");
        }

        queryVeiculos.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryVeiculos, "v", pageable);

        TypedQuery<Veiculo> query = em.createQuery(queryVeiculos.toString(), Veiculo.class);
        PaginacaoUtil.prepararIntervalo(query, pageable);

        if (StringUtils.hasText(filtro.getPlaca())) {
            query.setParameter("placa", "%" + filtro.getPlaca().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getModelo())) {
            query.setParameter("modelo", "%" + filtro.getModelo().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getMarca())) {
            query.setParameter("marca", "%" + filtro.getMarca().toLowerCase() + "%");
        }

        List<Veiculo> resultado = query.getResultList();

        StringBuilder jpqlCount = new StringBuilder("select count(v) from Veiculo v");
        jpqlCount.append(condicoes);
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        if (StringUtils.hasText(filtro.getPlaca())) {
            countQuery.setParameter("placa", "%" + filtro.getPlaca().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getModelo())) {
            countQuery.setParameter("modelo", "%" + filtro.getModelo().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getMarca())) {
            countQuery.setParameter("marca", "%" + filtro.getMarca().toLowerCase() + "%");
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }
}
