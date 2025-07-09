package web.onficina.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.onficina.filter.OficinaFilter;
import web.onficina.model.Oficina;
import web.onficina.model.Status;
import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.OficinaRepository;
import web.onficina.service.OficinaService;
import web.onficina.service.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;




@Controller
@RequestMapping("/oficina")
public class OficinaController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private OficinaRepository oficinaRepository;
    private OficinaService oficinaService;
    @Autowired
    private RelatorioService relatorioService;

    public OficinaController(OficinaRepository oficinaRepository, OficinaService oficinaService){
        this.oficinaRepository = oficinaRepository;
        this.oficinaService = oficinaService;
    }


    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("oficina", new Oficina());

        return "oficina/cadastrar :: formulario";
    }


    @PostMapping("/cadastrar")
    public String salvar(@Valid Oficina oficina, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "oficina/cadastrar :: formulario";
        }

        oficinaService.salvar(oficina);
        redirectAttributes.addAttribute("mensagem", "Oficina cadastrada com sucesso!");
        return "redirect:/oficina/listar";
    }


    @HxRequest
    @GetMapping("/listar")
    public String listar(OficinaFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Oficina> pagina = oficinaRepository.pesquisar(filtro, pageable);
        PageWrapper<Oficina> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        return "oficina/listar :: tabela";
    }


    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa() {
        return "oficina/pesquisar :: formulario";
    }


    @HxRequest
    @GetMapping("/pesquisar")
    public String pesquisar(OficinaFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Oficina> pagina = oficinaRepository.pesquisar(filtro, pageable);
        PageWrapper<Oficina> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        return "oficina/listar :: tabela";
    }

    
    @HxRequest
    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model) {
        Oficina oficina = oficinaRepository.findByIdAndStatus(id, Status.ATIVO);
        
        if (oficina != null) {
            model.addAttribute("oficina", oficina);
            return "oficina/alterar :: formulario";
        } else {
            model.addAttribute("mensagem", "Não existe uma oficina com esse código");
            return "mensagem :: texto";
        }
    }


    @HxRequest
    @PostMapping("/alterar")
    public String alterar(@Valid Oficina oficina, BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            logger.info("A oficina recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "oficina/alterar :: formulario";
        } else {

            oficinaService.alterar(oficina);
            redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Oficina alterada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/oficina/listar";
        }
    }

    
    @HxRequest
    @HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        oficinaService.remover(id);
        attributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Oficina removida com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "redirect:/oficina/listar";
    }


@GetMapping("/{id}/relatorio")
public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long id) {
    try {
        byte[] relatorio = relatorioService.gerarRelatorioOficina(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_oficina_" + id + ".pdf")
                .body(relatorio);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().build();
    }
}

}

