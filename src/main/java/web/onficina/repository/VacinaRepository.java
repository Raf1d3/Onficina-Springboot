package web.onficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Status;
import web.onficina.model.Vacina;
import web.onficina.repository.queries.vacina.VacinaQueries;

public interface VacinaRepository extends JpaRepository<Vacina, Long>, VacinaQueries {

    List<Vacina> findByStatus(Status status);

}
