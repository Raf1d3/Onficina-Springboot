package web.onficina.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Manutencao;
import web.onficina.model.Oficina;
import web.onficina.model.Status;
import web.onficina.repository.ManutencaoRepository;
import web.onficina.repository.OficinaRepository;

@Service
@Transactional
public class OficinaService {

    private OficinaRepository oficinaRepository;
    private ManutencaoRepository manutencaoRepository;
    private ManutencaoService manutencaoService;

    public OficinaService(OficinaRepository oficinaRepository, ManutencaoRepository manutencaoRepository, ManutencaoService manutencaoService) {
        this.oficinaRepository = oficinaRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.manutencaoService = manutencaoService;
    }

    public void salvar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    public void alterar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    public void remover(Long id) {
        Oficina oficina = oficinaRepository.findByIdAndStatus(id, Status.ATIVO);
        if (oficina == null) {
            throw new RuntimeException("Remoção de oficina com codigo inválido");
        } else {
            oficina.setStatus(Status.INATIVO);
            oficinaRepository.save(oficina);

            // 3. Busca todas as manutenções ATIVAS associadas a este veículo.
            List<Manutencao> manutencoesAtivas = manutencaoRepository.findByOficinaIdAndStatus(oficina.getId(), Status.ATIVO);

            // 4. Itera sobre a lista e desativa cada manutenção.
            for (Manutencao manutencao : manutencoesAtivas) {
                manutencaoService.remover(manutencao.getId());
            }
        }

    }

}
