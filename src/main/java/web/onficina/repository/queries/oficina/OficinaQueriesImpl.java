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
        StringBuilder queryOficinas = new StringBuilder("select o from Oficina o");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        if (StringUtils.hasText(filtro.getNome())) {
            condicoes.append(" and lower(o.nome) like :nome");
        }

        if (filtro.getNotaMedia() != null && filtro.getNotaMedia() > 0) {
            // Exemplo: buscando por oficinas com nota maior ou igual à filtrada
            condicoes.append(" and o.notaMedia >= :notaMedia"); 
        }


        if (StringUtils.hasText(filtro.getTelefone())) {
            condicoes.append(" and lower(o.telefone) like :telefone");
        }

        queryOficinas.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryOficinas, "o", pageable);

        TypedQuery<Oficina> query = em.createQuery(queryOficinas.toString(), Oficina.class);
        PaginacaoUtil.prepararIntervalo(query, pageable);

        if (StringUtils.hasText(filtro.getNome())) {
            query.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
        }
        if (filtro.getNotaMedia() != null && filtro.getNotaMedia() > 0) {
            query.setParameter("notaMedia", filtro.getNotaMedia()); 
        }

        if (StringUtils.hasText(filtro.getTelefone())) {
            query.setParameter("telefone", "%" + filtro.getTelefone().toLowerCase() + "%");
        }

        List<Oficina> resultado = query.getResultList();

        StringBuilder jpqlCount = new StringBuilder("select count(o) from Oficina o");
        jpqlCount.append(condicoes);
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        if (StringUtils.hasText(filtro.getNome())) {
            countQuery.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
        }
        if (filtro.getNotaMedia() != null && filtro.getNotaMedia() > 0) {
            countQuery.setParameter("notaMedia", filtro.getNotaMedia());
        }

        if (StringUtils.hasText(filtro.getTelefone())) {
            countQuery.setParameter("telefone", "%" + filtro.getTelefone().toLowerCase() + "%");
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }

}
