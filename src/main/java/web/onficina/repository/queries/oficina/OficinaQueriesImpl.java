package web.onficina.repository.queries.oficina;

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
import web.onficina.filter.OficinaFilter;
import web.onficina.model.Oficina;
import web.onficina.pagination.PaginacaoUtil;

public class OficinaQueriesImpl implements OficinaQueries {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Oficina> pesquisar(OficinaFilter filtro, Pageable pageable) {
        StringBuilder queryOficinas = new StringBuilder("select distinct o from Oficina o");
        StringBuilder condicoes = new StringBuilder();

        Map<String, Object> parametros = new HashMap<>();

        preencherCondicoesEParametros(filtro, condicoes, parametros);

        if (condicoes.isEmpty()) {
            condicoes.append(" where o.status = 'ATIVO'");
        } else {
            condicoes.append(" and o.status = 'ATIVO'");
        }

        queryOficinas.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryOficinas, "o", pageable);
        TypedQuery<Oficina> typedQuery = em.createQuery(queryOficinas.toString(), Oficina.class);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        PaginacaoUtil.preencherParametros(parametros, typedQuery);
        List<Oficina> Oficinas = typedQuery.getResultList();

        long totalOficinas = PaginacaoUtil.getTotalRegistros("Oficina", "o", condicoes, parametros, em);

        return new PageImpl<>(Oficinas, pageable, totalOficinas);
    }

    private void preencherCondicoesEParametros(OficinaFilter filtro, StringBuilder condicoes,
            Map<String, Object> parametros) {
        boolean condicao = false;

        if (filtro.getId() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("o.id = :id");
            parametros.put("id", filtro.getId());
            condicao = true;
        }
        if (StringUtils.hasText(filtro.getNome())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("lower(o.nome) like :nome");
            parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
            condicao = true;
        }
        if (StringUtils.hasText(filtro.getCnpj())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("o.cnpj like :cnpj");
            parametros.put("cnpj", "%" + filtro.getCnpj() + "%");
            condicao = true;
        }
        if (StringUtils.hasText(filtro.getEndereco())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("o.endereco like :endereco");
            parametros.put("endereco", "%" + filtro.getEndereco() + "%");
            condicao = true;
        }
        if (StringUtils.hasText(filtro.getTelefone())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("o.telefone like :telefone");
            parametros.put("telefone", "%" + filtro.getTelefone() + "%");
            condicao = true;
        }
        if (filtro.getNotaMedia() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("o.notaMedia = :notaMedia");
            parametros.put("notaMedia", filtro.getNotaMedia());
            condicao = true;
        }
    }

}
