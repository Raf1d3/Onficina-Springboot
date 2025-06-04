package web.onficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.onficina.model.Manutencao;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    // List<Manutencao> findByVeiculoId(Long veiculoId);
    // List<Manutencao> findByOficinaId(Long oficinaId);
}
