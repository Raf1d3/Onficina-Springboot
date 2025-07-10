package web.onficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import web.onficina.model.Manutencao;
import web.onficina.model.Status;
import web.onficina.repository.queries.manutencao.ManutencaoQueries;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long>, ManutencaoQueries {
     List<Manutencao> findByVeiculoIdAndStatus(Long veiculoId, Status status);
     List<Manutencao> findByOficinaIdAndStatus(Long oficinaId, Status status);
     List<Manutencao> findByOficinaId(Long oficinaId);
     List<Manutencao> findAllByVeiculo_Proprietario_IdAndStatus(Long proprietarioId, Status status);
     Manutencao findByIdAndStatus(Long id, Status status);
}
