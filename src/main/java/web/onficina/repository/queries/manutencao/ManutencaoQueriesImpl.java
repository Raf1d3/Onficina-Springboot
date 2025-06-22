package web.onficina.repository.queries.manutencao; 

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
import web.onficina.filter.ManutencaoFilter;
import web.onficina.model.Manutencao;
import web.onficina.pagination.PaginacaoUtil;

public class ManutencaoQueriesImpl implements ManutencaoQueries {

    private static final Logger logger = LoggerFactory.getLogger(ManutencaoQueriesImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Manutencao> pesquisar(ManutencaoFilter filtro, Pageable pageable) {
     
        StringBuilder queryManutencoes = new StringBuilder("select m from Manutencao m left join m.veiculo v left join m.oficina o");
        StringBuilder condicoes = new StringBuilder(" where 1=1");

        if (StringUtils.hasText(filtro.getDescricao())) {
            condicoes.append(" and lower(m.descricao) like :descricao");
        }
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
        if (StringUtils.hasText(filtro.getVeiculoPlaca())) {
            condicoes.append(" and lower(v.placa) like :veiculoPlaca");
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            condicoes.append(" and m.oficina.id = :oficinaId");
        }
         if (StringUtils.hasText(filtro.getOficinaNome())) {
            condicoes.append(" and lower(o.nome) like :oficinaNome");
        }
        if (filtro.getDataInicioManutencaoApos() != null) {
            condicoes.append(" and m.dataInicioManutencao >= :dataInicioManutencaoApos");
        }
        if (filtro.getDataInicioManutencaoAntes() != null) {
            condicoes.append(" and m.dataInicioManutencao <= :dataInicioManutencaoAntes");
        }
        if (filtro.getDataProximaManutencaoApos() != null) {
            condicoes.append(" and m.dataProximaManutencao >= :dataProximaManutencaoApos");
        }
        if (filtro.getDataProximaManutencaoAntes() != null) {
            condicoes.append(" and m.dataProximaManutencao <= :dataProximaManutencaoAntes");
        }


        queryManutencoes.append(condicoes);
        PaginacaoUtil.prepararOrdemJPQL(queryManutencoes, "m", pageable); 

        TypedQuery<Manutencao> query = em.createQuery(queryManutencoes.toString(), Manutencao.class);
        PaginacaoUtil.prepararIntervalo(query, pageable);

        if (StringUtils.hasText(filtro.getDescricao())) {
            query.setParameter("descricao", "%" + filtro.getDescricao().toLowerCase() + "%");
        }
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
        if (StringUtils.hasText(filtro.getVeiculoPlaca())) {
            query.setParameter("veiculoPlaca", "%" + filtro.getVeiculoPlaca().toLowerCase() + "%");
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            query.setParameter("oficinaId", filtro.getOficinaId());
        }
        if (StringUtils.hasText(filtro.getOficinaNome())) {
            query.setParameter("oficinaNome", "%" + filtro.getOficinaNome().toLowerCase() + "%");
        }
        if (filtro.getDataInicioManutencaoApos() != null) {
            query.setParameter("dataInicioManutencaoApos", filtro.getDataInicioManutencaoApos());
        }
        if (filtro.getDataInicioManutencaoAntes() != null) {
            query.setParameter("dataInicioManutencaoAntes", filtro.getDataInicioManutencaoAntes());
        }
        if (filtro.getDataProximaManutencaoApos() != null) {
            query.setParameter("dataProximaManutencaoApos", filtro.getDataProximaManutencaoApos());
        }
        if (filtro.getDataProximaManutencaoAntes() != null) {
            query.setParameter("dataProximaManutencaoAntes", filtro.getDataProximaManutencaoAntes());
        }


        List<Manutencao> resultado = query.getResultList();

        StringBuilder jpqlCount = new StringBuilder("select count(m.id) from Manutencao m left join m.veiculo v left join m.oficina o");
        jpqlCount.append(condicoes); 
        TypedQuery<Long> countQuery = em.createQuery(jpqlCount.toString(), Long.class);

        if (StringUtils.hasText(filtro.getDescricao())) {
            countQuery.setParameter("descricao", "%" + filtro.getDescricao().toLowerCase() + "%");
        }
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
         if (StringUtils.hasText(filtro.getVeiculoPlaca())) {
            countQuery.setParameter("veiculoPlaca", "%" + filtro.getVeiculoPlaca().toLowerCase() + "%");
        }
        if (filtro.getOficinaId() != null && filtro.getOficinaId() > 0) {
            countQuery.setParameter("oficinaId", filtro.getOficinaId());
        }
         if (StringUtils.hasText(filtro.getOficinaNome())) {
            countQuery.setParameter("oficinaNome", "%" + filtro.getOficinaNome().toLowerCase() + "%");
        }
        if (filtro.getDataInicioManutencaoApos() != null) {
            countQuery.setParameter("dataInicioManutencaoApos", filtro.getDataInicioManutencaoApos());
        }
        if (filtro.getDataInicioManutencaoAntes() != null) {
            countQuery.setParameter("dataInicioManutencaoAntes", filtro.getDataInicioManutencaoAntes());
        }
        if (filtro.getDataProximaManutencaoApos() != null) {
            countQuery.setParameter("dataProximaManutencaoApos", filtro.getDataProximaManutencaoApos());
        }
        if (filtro.getDataProximaManutencaoAntes() != null) {
            countQuery.setParameter("dataProximaManutencaoAntes", filtro.getDataProximaManutencaoAntes());
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultado, pageable, total);
    }
}