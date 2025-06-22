package web.onficina.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.onficina.model.Manutencao;
import web.onficina.repository.ManutencaoRepository;


@Service
@Transactional
public class ManutencaoService {


    private ManutencaoRepository manutencaoRepository;

    public ManutencaoService(ManutencaoRepository manutencaoRepository) {
        this.manutencaoRepository = manutencaoRepository;
    }

    public void salvar(Manutencao manutencao) {
        manutencaoRepository.save(manutencao);
    }

    public void alterar(Manutencao manutencao) {
        manutencaoRepository.save(manutencao);
    }

    public void remover(Long codigo) {
        manutencaoRepository.deleteById(codigo);
    }
}