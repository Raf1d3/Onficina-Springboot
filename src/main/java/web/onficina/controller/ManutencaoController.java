package web.onficina.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

import org.springframework.ui.Model;
import org.springframework.data.domain.Sort;

import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
// import web.onficina.filter.ManutencaoFilter;
import web.onficina.model.Usuario;
import web.onficina.model.Veiculo;
import web.onficina.model.Manutencao;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.ManutencaoRepository;
import web.onficina.repository.VeiculoRepository;
import web.onficina.service.ManutencaoService;

@Controller
@RequestMapping("/manutencao")
public class ManutencaoController {

    private static final Logger logger = LoggerFactory.getLogger(ManutencaoController.class);

    private ManutencaoRepository manutencaoRepository;
    private ManutencaoService manutencaoService;
    private VeiculoRepository veiculoRepository;

    public ManutencaoController(ManutencaoRepository manutencaoRepository, ManutencaoService manutencaoService, VeiculoRepository veiculoRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.manutencaoService = manutencaoService;
        this.veiculoRepository = veiculoRepository;
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            model.addAttribute("erro", "Usuário não está logado.");
            return "redirect:/login";
        }

        List<Veiculo> veiculos = veiculoRepository.findAllByProprietarioId(usuarioLogado.getId());

        model.addAttribute("veiculos", veiculos);
        model.addAttribute("manutencao", new Manutencao());

        return "manutencao/cadastrar :: formulario";
    }

    @PostMapping("/cadastrar")
    public String salvar(@Valid Manutencao manutencao, BindingResult result, 
            Model model, RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (result.hasErrors()) {
            return "manutencao/cadastrar :: formulario";
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
            return "redirect:/login";
        }

        Optional<Veiculo> optVeiculo = veiculoRepository.findById(manutencao.getVeiculo().getId());
        if (!optVeiculo.isPresent() || !optVeiculo.get().getProprietario().getId().equals(usuarioLogado.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Veículo inválido.");
            return "redirect:/manutencao/cadastrar";
        }
    
        manutencao.setVeiculo(optVeiculo.get());

        manutencaoService.salvar(manutencao);
        redirectAttributes.addFlashAttribute("notificacao", 
    new NotificacaoSweetAlert2("Manutenção cadastrada com sucesso!", 
    TipoNotificaoSweetAlert2.SUCCESS, 4000));

        return "redirect:/manutencao/listar";
    }

    // @HxRequest
    // @GetMapping("/listar")
    // public String listar(ManutencaoFilter filtro, Model model,
    //         @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    //         HttpServletRequest request) {

    //     Page<Manutencao> pagina = manutencaoRepository.pesquisar(filtro, pageable);
    //     PageWrapper<Manutencao> paginaWrapper = new PageWrapper<>(pagina, request);

    //     model.addAttribute("pagina", paginaWrapper);
    //     return "manutencao/listar :: tabela";
    // }


    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa() {
        return "manutencao/pesquisar :: formulario";
    }


    // @HxRequest
    // @GetMapping("/pesquisar")
    // public String pesquisar(ManutencaoFilter filtro, Model model,
    //         @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    //         HttpServletRequest request) {

    //     Page<Manutencao> pagina = manutencaoRepository.pesquisar(filtro, pageable);
    //     PageWrapper<Manutencao> paginaWrapper = new PageWrapper<>(pagina, request);

    //     model.addAttribute("pagina", paginaWrapper);
    //     return "manutencao/listar :: tabela";
    // }
    
    @HxRequest
    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);
        if (manutencao != null) {
            model.addAttribute("manutencao", manutencao);
            return "manutencao/alterar :: formulario";
        } else {
            model.addAttribute("mensagem", "Não existe uma manutenção com esse código");
            return "mensagem :: texto";
        }
    }

    // @HxRequest
    // @PostMapping("/alterar")
    // public String alterar(@Valid Manutencao manutencao, BindingResult resultado,
    //         RedirectAttributes redirectAttributes, HttpSession session) {
    //     if (resultado.hasErrors()) {
    //         logger.info("A manutenção recebida para cadastrar não é válida.");
    //         logger.info("Erros encontrados:");
    //         for (FieldError erro : resultado.getFieldErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         for (ObjectError erro : resultado.getGlobalErrors()) {
    //             logger.info("{}", erro);
    //         }
    //         return "manutencao/alterar :: formulario";
    //     } else {

    //         Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    //         if (usuarioLogado == null) {
    //             redirectAttributes.addFlashAttribute("erro", "Usuário não está logado.");
    //             return "redirect:/login";
    //         }

    //         manutencao.setProprietario(usuarioLogado);

    //         manutencaoService.alterar(manutencao);
    //         redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2("Manutenção alterada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));  // redirect
    //         return "redirect:/manutencao/listar";
    //     }
    // }

    @HxRequest
    @HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        manutencaoService.remover(id);
        attributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Manutenção removida com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000)); // redirect
        return "redirect:/manutencao/listar";
    }

}
