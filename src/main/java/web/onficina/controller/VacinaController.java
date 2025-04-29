package web.onficina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import web.onficina.filter.VacinaFilter;
import web.onficina.model.Status;
import web.onficina.model.Vacina;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.VacinaRepository;
import web.onficina.service.VacinaService;

@Controller
public class VacinaController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    private VacinaRepository vacinaRepository;
    private VacinaService vacinaService;

    public VacinaController(VacinaRepository vacinaRepository, VacinaService vacinaService) {
        this.vacinaRepository = vacinaRepository;
        this.vacinaService = vacinaService;
    }

    @GetMapping("/vacinas")
    public String mostrarTodasVacinas(Model model) {
        List<Vacina> vacinas = vacinaRepository.findByStatus(Status.ATIVO);
        model.addAttribute("vacinas", vacinas);
        return "vacinas/listar";
    }

    @GetMapping("/vacinas/abrirpesquisar")
    public String abrirPesquisar() {
        return "vacinas/pesquisar";
    }

    @GetMapping("/vacinas/pesquisar")
    public String pesquisar(VacinaFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "codigo", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Vacina> pagina = vacinaRepository.pesquisar(filtro, pageable);
        logger.trace("Vacinas pesquisadas: {}", pagina.getContent());
        PageWrapper<Vacina> paginaWrapper = new PageWrapper<>(pagina, request);
        model.addAttribute("pagina", paginaWrapper);
        return "vacinas/listar";
    }

    @GetMapping("/vacinas/cadastrar")
    public String abrirCadastro(Vacina vacina) {
        return "vacinas/cadastrar";
    }

    @HxRequest
    @GetMapping("/vacinas/cadastrar")
    public String abrirCadastroHTMX(Vacina vacina) {
        return "vacinas/cadastrar :: formulario";
    }

    @PostMapping("/vacinas/cadastrar")
    public String cadastrar(Vacina vacina, RedirectAttributes atributos) {
        vacinaService.salvar(vacina);
        atributos.addAttribute("mensagem", "Vacina cadastrada com sucesso");
        return "redirect:/mensagem";
    }

    @GetMapping("/mensagem")
    public String mostrarMensagem(String mensagem, Model model) {
        model.addAttribute("mensagem", mensagem);
        return "mensagem";
    }

    @HxRequest
    @HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
    @PostMapping("/vacinas/cadastrar")
    public String cadastrarHTMX(Vacina vacina, RedirectAttributes redirectAttributes) {
        vacinaService.salvar(vacina);
        redirectAttributes.addAttribute("mensagem", "Vacina cadastrada com sucesso");
        return "redirect:/mensagem";
    }

    @HxRequest
    @GetMapping("/mensagem")
    public String mostrarMensagemHTMX(String mensagem, Model model) {
        if (mensagem != null && !mensagem.isEmpty()) {
            model.addAttribute("mensagem", mensagem);
        }
        return "mensagem :: texto";
    }

    @GetMapping("/vacinas/alterar/{codigo}")
    public String abrirAlterar(@PathVariable("codigo") Long codigo, Model model) {
        Vacina vacina = vacinaRepository.findById(codigo).orElse(null);
        if (vacina != null) {
            model.addAttribute("vacina", vacina);
            return "vacinas/alterar";
        } else {
            model.addAttribute("mensagem", "Código de Vacina inválido");
            return "mensagem";
        }
    }

    @PostMapping("/vacinas/alterar")
    public String alterar(Vacina vacina, RedirectAttributes atributos) {
        vacinaService.alterar(vacina);
        atributos.addAttribute("mensagem", "Vacina alterada com sucesso");
        return "redirect:/mensagem";
    }

    @GetMapping("/vacinas/remover/{codigo}")
    public String remover(@PathVariable("codigo") Long codigo, RedirectAttributes atributos) {
        vacinaService.desativar(codigo);
        atributos.addAttribute("mensagem", "Vacina removida com sucesso");
        return "redirect:/mensagem";
    }

}
