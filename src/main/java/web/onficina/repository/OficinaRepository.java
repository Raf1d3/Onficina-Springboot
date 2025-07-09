package web.onficina.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Oficina;
import web.onficina.model.Status;
import web.onficina.repository.queries.oficina.OficinaQueries;


public interface OficinaRepository extends JpaRepository<Oficina, Long>, OficinaQueries{
    
    Oficina findByCnpj(String cnpj);
    Oficina findByIdAndStatus(Long id, Status status);
    List<Oficina> findAllByStatus(Status status);
}
