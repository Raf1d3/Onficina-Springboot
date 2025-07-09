package web.onficina.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.onficina.model.Status;
import web.onficina.model.Veiculo;
import web.onficina.repository.queries.veiculo.VeiculoQueries;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, VeiculoQueries{

    List<Veiculo> findAllByProprietarioIdAndStatus(Long usuarioId, Status status);
    List<Veiculo> findAllByStatus(Status status);
    Veiculo findByIdAndStatus(Long id, Status status);
    Veiculo findByPlacaIgnoreCase(String placa);
    
}
    

