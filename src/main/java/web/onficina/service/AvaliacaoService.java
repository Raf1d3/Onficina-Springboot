package web.onficina.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import web.onficina.model.Avaliacao;
import web.onficina.model.Oficina;
import web.onficina.model.Status;
import web.onficina.repository.AvaliacaoRepository;
import web.onficina.repository.OficinaRepository;

@Service
@Transactional
public class AvaliacaoService {

    private AvaliacaoRepository avaliacaoRepository;
    private OficinaRepository oficinaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, OficinaRepository oficinaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.oficinaRepository = oficinaRepository;
    }

    @Transactional
    public void salvar(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);

        Oficina oficina = avaliacao.getOficina();
        recalcularNotaMedia(oficina);
    }

    public void alterar(Avaliacao avaliacao) {
        avaliacaoRepository.save(avaliacao);

        Oficina oficina = avaliacao.getOficina();
        recalcularNotaMedia(oficina);
    }

    public void remover(Long codigo) {
        Avaliacao avaliacao = avaliacaoRepository.findByIdAndStatus(codigo, Status.ATIVO);
        if (avaliacao == null) {
            throw new RuntimeException("Remoção da avaliação com codigo inválido");
        } else {
            avaliacao.setStatus(Status.INATIVO);
            avaliacaoRepository.save(avaliacao);

            Oficina oficina = avaliacao.getOficina();
            recalcularNotaMedia(oficina);
        }
    }

    private void recalcularNotaMedia(Oficina oficina) {
        Oficina oficinaBanco = oficinaRepository.findByIdAndStatus(oficina.getId(), Status.ATIVO);

        if (oficinaBanco == null) {
            return;
        }

        Double novaMedia = avaliacaoRepository.calcularNotaMediaPorOficina(oficinaBanco.getId());

        // Se novaMedia for null (nenhuma avaliação ativa), nota definida como 0.0
        double mediaFinal = (novaMedia != null) ? novaMedia : 0.0;

        BigDecimal bd = new BigDecimal(mediaFinal).setScale(2, RoundingMode.HALF_UP);
        oficinaBanco.setNotaMedia(bd.doubleValue());
        oficinaRepository.save(oficinaBanco);
    }

}
