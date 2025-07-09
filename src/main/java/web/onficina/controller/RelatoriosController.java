package web.onficina.controller;

// Adicione esta importação se não existir
import java.io.InputStream; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User; // Importação correta
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.JRException;
import web.onficina.model.Usuario;
import web.onficina.service.RelatorioService;
import web.onficina.service.UsuarioService;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

    @Autowired
    private RelatorioService relatorioService;
    
    @Autowired
    private UsuarioService usuarioService;

   @GetMapping("/meu-historico-veiculos")
public ResponseEntity<byte[]> gerarRelatorioHistoricoVeiculos(@AuthenticationPrincipal User usuarioLogado) {
    try {
        // Debug
        System.out.println("--- INICIANDO DEPURAÇÃO DO RELATÓRIO ---");
        String caminhoSubRelatorio = "/reports/sub_relatorio_manutencoes.jasper";
        InputStream streamTeste = getClass().getResourceAsStream(caminhoSubRelatorio);
        if (streamTeste == null) {
            System.err.println(">>>>>> ERRO CRÍTICO: O Java NÃO conseguiu encontrar o arquivo: " + caminhoSubRelatorio);
            throw new RuntimeException("Falha ao carregar recurso do sub-relatório. Verifique o caminho e o nome do arquivo.");
        } else {
            System.out.println("====== SUCESSO NA DEPURAÇÃO: O Java encontrou o arquivo do sub-relatório! ======");
            streamTeste.close();
        }

        if (usuarioLogado == null) {
            return ResponseEntity.status(401).build();
        }
        
        String email = usuarioLogado.getUsername();
        Usuario clienteCompleto = usuarioService.findByEmail(email);

        byte[] relatorio = relatorioService.gerarRelatorioHistoricoVeiculos(clienteCompleto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=historico_veiculos.pdf")
                .body(relatorio);

    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(("Erro ao gerar relatório: " + e.getMessage()).getBytes());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(("Erro inesperado: " + e.getMessage()).getBytes());
    }
}
}