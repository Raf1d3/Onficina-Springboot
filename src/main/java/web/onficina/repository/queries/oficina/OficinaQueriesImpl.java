package web.onficina.repository.queries.oficina;

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
import web.onficina.filter.OficinaFilter;
import web.onficina.model.Oficina;
import web.onficina.pagination.PaginacaoUtil;

public class OficinaQueriesImpl implements OficinaQueries {

    private static final Logger logger = LoggerFactory.getLogger(OficinaQueriesImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Oficina> pesquisar(OficinaFilter filtro, Pageable pageable) {
        StringBuilder queryOficinas = new StringBuilder("select v from Oficina v");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        if (StringUtils.hasText(filtro.getNome())) {
            condicoes.append(" and lower(v.nome) like :nome");
        }

        if (StringUtils.hasText(filtro.getNotaMedia())) {
            condicoes.append(" and lower(v.nota_media) like :nota_media");
        }

        if (StringUtils.hasText(filtro.getTelefone())) {
            condicoes.append(" and lower(v.telefone) like :telefone");
        }

        queryOficinas.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryOficinas, "v", pageable);

        TypedQuery<Oficina> query = em.createQuery(queryOficinas.toString(), Oficina.class);
        PaginacaoUtil.prepararIntervalo(query, pageable);

        if (StringUtils.hasText(filtro.getNome())) {
            query.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getNotaMedia())) {
            query.setParameter("nota_media", "%" + filtro.getNotaMedia().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getTelefone())) {
            query.setParameter("telefone", "%" + filtro.getTelefone().toLowerCase() + "%");
        }

        List<Oficina> resultado = query.getResultList();

        StringBuilder jpqlCount = new StringBuilder("select count(v) from Oficina v");
        jpqlCount.append(condicoes);
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        if (StringUtils.hasText(filtro.getNome())) {
            countQuery.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getNotaMedia())) {
            countQuery.setParameter("nota_media", "%" + filtro.getNotaMedia().toLowerCase() + "%");
        }
        if (StringUtils.hasText(filtro.getTelefone())) {
            countQuery.setParameter("telefone", "%" + filtro.getTelefone().toLowerCase() + "%");
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }

}
