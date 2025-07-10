package web.onficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import web.onficina.model.Avaliacao;
import web.onficina.model.Status;
import web.onficina.repository.queries.avaliacao.AvaliacaoQueries;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>, AvaliacaoQueries {
    List<Avaliacao> findByProprietarioId(Long proprietarioId);

    List<Avaliacao> findByOficinaId(Long oficinaId);

    List<Avaliacao> findByManutencaoIdAndStatus(Long oficinaId, Status status);

    Avaliacao findByIdAndStatus(Long id, Status status);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.oficina.id = :oficinaId AND a.status = 'ATIVO'")
    Double calcularNotaMediaPorOficina(@Param("oficinaId") Long oficinaId);
}
