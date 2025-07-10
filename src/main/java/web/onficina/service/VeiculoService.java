package web.onficina.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Manutencao;
import web.onficina.model.Status;
import web.onficina.model.Veiculo;
import web.onficina.repository.ManutencaoRepository;
import web.onficina.repository.VeiculoRepository;

@Service
@Transactional
public class VeiculoService {

    private VeiculoRepository veiculoRepository;
    private ManutencaoRepository manutencaoRepository;
    private ManutencaoService manutencaoService;

    public VeiculoService(VeiculoRepository veiculoRepository, ManutencaoRepository manutencaorepository, ManutencaoService manutencaoService) {
        this.manutencaoRepository = manutencaorepository;
        this.veiculoRepository = veiculoRepository;
        this.manutencaoService = manutencaoService;
    }

    public void salvar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void alterar(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public void remover(Long id) {
        Veiculo veiculo = veiculoRepository.findByIdAndStatus(id, Status.ATIVO);
        if (veiculo == null) {
            throw new RuntimeException("Remoção de veiculo com codigo inválido");
        } else {
            veiculo.setStatus(Status.INATIVO);
            veiculoRepository.save(veiculo);

            // 3. Busca todas as manutenções ATIVAS associadas a este veículo.
            List<Manutencao> manutencoesAtivas = manutencaoRepository.findByVeiculoIdAndStatus(veiculo.getId(), Status.ATIVO);

            // 4. Itera sobre a lista e desativa cada manutenção.
            for (Manutencao manutencao : manutencoesAtivas) {
                manutencaoService.remover(manutencao.getId());
            }
        }

    }

}
