package web.onficina.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Oficina;
import web.onficina.repository.queries.oficina.OficinaQueries;


public interface OficinaRepository extends JpaRepository<Oficina, Long>, OficinaQueries{
    
    Oficina findByCnpj(String cnpj);

}
