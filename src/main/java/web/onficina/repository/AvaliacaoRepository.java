package web.onficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import web.onficina.model.Avaliacao;
import web.onficina.model.Status;
import web.onficina.repository.queries.avaliacao.AvaliacaoQueries;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long>, AvaliacaoQueries{
    List<Avaliacao> findByProprietarioId(Long proprietarioId);
    List<Avaliacao> findByOficinaId(Long oficinaId);
    Avaliacao findByIdAndStatus(Long id, Status status);
}
     