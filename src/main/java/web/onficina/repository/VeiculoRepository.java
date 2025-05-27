package web.onficina.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Veiculo;
import web.onficina.repository.queries.veiculo.VeiculoQueries;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, VeiculoQueries{

    Veiculo findByProprietarioId(Long usuarioId);
    
}
    

