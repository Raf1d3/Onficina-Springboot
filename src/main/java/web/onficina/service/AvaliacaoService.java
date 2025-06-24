package web.onficina.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import web.onficina.model.Avaliacao;
import web.onficina.repository.AvaliacaoRepository;

@Service
@Transactional
public class AvaliacaoService {

    private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public void salvar(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);
    }

    public void alterar(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);
    }

    public void remover(Long codigo) {
        avaliacaoRepository.deleteById(codigo);
    }

}
