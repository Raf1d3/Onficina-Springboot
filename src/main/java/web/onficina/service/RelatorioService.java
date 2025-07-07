package web.onficina.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.onficina.model.Avaliacao;
import web.onficina.model.AvaliacaoReportDTO;
import web.onficina.model.Oficina;
import web.onficina.repository.OficinaRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RelatorioService {

    @Autowired
    private OficinaRepository oficinaRepository; 

public byte[] gerarRelatorioOficina(Long oficinaId) throws JRException {
    InputStream relatorioCompilado = getClass().getResourceAsStream("/reports/relatorio_oficina.jasper");
    if (relatorioCompilado == null) {
        throw new JRException("Arquivo de relatório não encontrado: relatorio_oficina.jasper");
    }

    Oficina oficina = oficinaRepository.findById(oficinaId)
            .orElseThrow(() -> new RuntimeException("Oficina com ID " + oficinaId + " não encontrada."));

    // 1. Pega a lista de entidades 'Avaliacao'.
    java.util.List<Avaliacao> avaliacoes = oficina.getAvaliacoes();
    // 2. Converte cada 'Avaliacao' em um 'AvaliacaoReportDTO'.
    java.util.List<AvaliacaoReportDTO> avaliacoesDTO = new java.util.ArrayList<>();
    for (Avaliacao avaliacao : avaliacoes) {
        avaliacoesDTO.add(new AvaliacaoReportDTO(avaliacao));
    }
    // 3. Cria o DataSource com a nova lista de DTOs.
    JRBeanCollectionDataSource dataSourceAvaliacoes = new JRBeanCollectionDataSource(avaliacoesDTO);

    Map<String, Object> parametros = new HashMap<>();
    parametros.put("P_NOME_OFICINA", oficina.getNome());
    parametros.put("P_CNPJ", oficina.getCnpj());
    parametros.put("P_ENDERECO", oficina.getEndereco());
    parametros.put("P_TELEFONE", oficina.getTelefone());
    parametros.put("P_NOTA_MEDIA", oficina.getNotaMedia());

    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorioCompilado, parametros, dataSourceAvaliacoes);

    return JasperExportManager.exportReportToPdf(relatorioPreenchido);
}
}