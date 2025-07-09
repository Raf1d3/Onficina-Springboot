package web.onficina.repository.queries.usuario;

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
import web.onficina.filter.UsuarioFilter;
import web.onficina.model.Usuario;
import web.onficina.pagination.PaginacaoUtil;

public class UsuarioQueriesImpl implements UsuarioQueries {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable) {
        StringBuilder queryUsuarios = new StringBuilder("select distinct u from Usuario u");
        queryUsuarios.append(" left join u.papel p");

        StringBuilder condicoes = new StringBuilder();
        Map<String, Object> parametros = new HashMap<>();

        preencherCondicoesEParametros(filtro, condicoes, parametros);

        queryUsuarios.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryUsuarios, "u", pageable);

        TypedQuery<Usuario> typedQuery = em.createQuery(queryUsuarios.toString(), Usuario.class);
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
        PaginacaoUtil.preencherParametros(parametros, typedQuery);
        List<Usuario> usuarios = typedQuery.getResultList();

        long totalUsuarios = PaginacaoUtil.getTotalRegistros("Usuario", "u", condicoes, parametros, em);

        return new PageImpl<>(usuarios, pageable, totalUsuarios);
    }

    private void preencherCondicoesEParametros(UsuarioFilter filtro, StringBuilder condicoes,
            Map<String, Object> parametros) {
        boolean condicao = false;
        
        if (filtro.getId() != null) {
			PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
			condicoes.append("u.id = :id");
			parametros.put("id", filtro.getId());
			condicao = true;
		}

        if (StringUtils.hasText(filtro.getNome())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("lower(u.nome) like :nome");
            parametros.put("nome", "%" + filtro.getNome().toLowerCase() + "%");
            condicao = true;
        }

        if (StringUtils.hasText(filtro.getEmail())) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("lower(u.email) like :email");
            parametros.put("email", "%" + filtro.getEmail().toLowerCase() + "%");
            condicao = true;
        }

        if (filtro.getPapelId() != null && filtro.getPapelId() > 0) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("u.papel.id = :papelId");
            parametros.put("papelId", filtro.getPapelId());
            condicao = true;
        }

        if (filtro.getAtivo() != null) {
            PaginacaoUtil.fazerLigacaoCondicoes(condicoes, condicao);
            condicoes.append("u.ativo = :ativo");
            parametros.put("ativo", filtro.getAtivo());
            condicao = true; // Boa prática manter
        }
    }

}
