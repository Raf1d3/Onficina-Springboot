package web.onficina.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Veiculo;
import web.onficina.repository.queries.veiculo.VeiculoQueries;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, VeiculoQueries{

    List<Veiculo> findAllByProprietarioId(Long usuarioId);

    
}
    

