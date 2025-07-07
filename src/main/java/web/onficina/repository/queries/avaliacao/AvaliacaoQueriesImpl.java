package web.onficina.repository.queries.avaliacao;

import java.util.List;

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

public class AvaliacaoQueriesImpl implements AvaliacaoQueries{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Avaliacao> pesquisar(AvaliacaoFilter filtro, Pageable pageable) {
        // Usa o alias 'a' para Avaliacao
        StringBuilder jpql = new StringBuilder("select a from Avaliacao a");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        // --- Adaptação dos Filtros ---
        // Para Enums, verificamos se não são nulos, em vez de usar StringUtils.hasText

        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            condicoes.append(" and a.veiculo.id = :veiculoId");
        }
        if (filtro.getProprietarioId() != null && filtro.getProprietarioId() > 0) {
            condicoes.append(" and a.proprietario.id = :proprietarioId");
        }
        if (filtro.getManutencaoId() != null && filtro.getManutencaoId() > 0) {
            condicoes.append(" and a.manutencao.id = :manutencaoId");
        }
        if (filtro.getNota() != null) {
            condicoes.append(" and a.nota = :nota");
        }
        if (filtro.getComentario() != null) {
            condicoes.append(" and lower(a.comentario) like lower(:comentario)");
        }
        // Adiciona um filtro extra para buscar manutenções de um veículo específico, se
        // necessário
        // if (filtro.getVeiculo() != null) {
        // condicoes.append(" and a.veiculo = :veiculo");
        // }

        jpql.append(condicoes);
        // Prepara a ordenação (ex: ?sort=valorServico,desc)
        PaginacaoUtil.prepararOrdemJPQL(jpql, "a", pageable);

        TypedQuery<Avaliacao> query = em.createQuery(jpql.toString(), Avaliacao.class);
        // Prepara o intervalo da paginação (quais registros buscar)
        PaginacaoUtil.prepararIntervalo(query, pageable);

        // --- Adaptação dos Parâmetros ---
        // Para Enums, passamos o objeto do enum diretamente, sem '%' ou toLowerCase()
        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            query.setParameter("veiculoId", filtro.getVeiculoId());
        }
        if (filtro.getProprietarioId() != null && filtro.getProprietarioId() > 0) {
            query.setParameter("proprietarioId", filtro.getProprietarioId());
        }
        if (filtro.getManutencaoId() != null && filtro.getManutencaoId() > 0) {
            query.setParameter("manutencaoId", filtro.getManutencaoId());
        }
        if (filtro.getNota() != null) {
            query.setParameter("nota", filtro.getNota());
        }
        if (filtro.getComentario() != null) {
            query.setParameter("comentario", "%" + filtro.getComentario() + "%");
        }

        List<Avaliacao> resultado = query.getResultList();

        // --- Query para Contagem Total de Registros (para a paginação) ---
        StringBuilder jpqlCount = new StringBuilder("select count(a) from Avaliacao a");
        jpqlCount.append(condicoes);
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        // Define os mesmos parâmetros para a query de contagem
        if (filtro.getVeiculoId() != null && filtro.getVeiculoId() > 0) {
            countQuery.setParameter("veiculoId", filtro.getVeiculoId());
        }
        if (filtro.getProprietarioId() != null && filtro.getProprietarioId() > 0) {
            countQuery.setParameter("proprietarioId", filtro.getProprietarioId());
        }
        if (filtro.getManutencaoId() != null && filtro.getManutencaoId() > 0) {
            countQuery.setParameter("manutencaoId", filtro.getManutencaoId());
        }
        if (filtro.getNota() != null) {
            countQuery.setParameter("nota", filtro.getNota());
        }
        if (filtro.getComentario() != null) {
            countQuery.setParameter("comentario", filtro.getComentario());
        }
        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }
}
