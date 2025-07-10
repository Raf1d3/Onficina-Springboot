package web.onficina.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.onficina.model.Avaliacao;
import web.onficina.model.Manutencao;
import web.onficina.model.Status;
import web.onficina.repository.AvaliacaoRepository;
import web.onficina.repository.ManutencaoRepository;


@Service
@Transactional
public class ManutencaoService {


    private ManutencaoRepository manutencaoRepository;
    private AvaliacaoRepository avaliacaoRepository;
    private AvaliacaoService avaliacaoService;
    

    public ManutencaoService(ManutencaoRepository manutencaoRepository, AvaliacaoRepository avaliacaoRepository, AvaliacaoService avaliacaoService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoService = avaliacaoService;
        this.manutencaoRepository = manutencaoRepository;
    }

    public void salvar(Manutencao manutencao) {
        manutencaoRepository.save(manutencao);
    }

    public void alterar(Manutencao manutencao) {
        manutencaoRepository.save(manutencao);
    }

    public void remover(Long codigo) {
        Manutencao manutencao = manutencaoRepository.findByIdAndStatus(codigo, Status.ATIVO);
        if (manutencao == null) {
            throw new RuntimeException("Remoção de manutenção com codigo inválido");
        } else {
            manutencao.setStatus(Status.INATIVO);
            manutencaoRepository.save(manutencao);

            List<Avaliacao> avaliacoesAtivas = avaliacaoRepository.findByManutencaoIdAndStatus(manutencao.getId(), Status.ATIVO);

            for (Avaliacao avaliacao : avaliacoesAtivas) {
                avaliacaoService.remover(avaliacao.getId());
            }


        }
    }
}
