package web.onficina.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Status;
import web.onficina.model.Veiculo;
import web.onficina.repository.VeiculoRepository;

@Service
@Transactional
public class VeiculoService {
    

    private VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public void salvar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void alterar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void remover(Long id) {
        Veiculo veiculo = veiculoRepository.findByIdAndStatus(id, Status.ATIVO);
        if(veiculo == null){
            throw new RuntimeException("Remoção de veiculo com codigo inválido");
        }else{
            veiculo.setStatus(Status.INATIVO);
            veiculoRepository.save(veiculo);
        }

    }


}
