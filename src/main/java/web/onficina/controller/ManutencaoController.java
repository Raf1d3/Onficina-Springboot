package web.onficina.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;
import web.onficina.pagination.PageWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.onficina.model.Usuario;
import web.onficina.model.Veiculo;
import web.onficina.filter.ManutencaoFilter;
import web.onficina.filter.VeiculoFilter;
import web.onficina.model.Manutencao;
import web.onficina.model.Oficina;
import web.onficina.repository.ManutencaoRepository;
import web.onficina.repository.OficinaRepository;
import web.onficina.repository.UsuarioRepository;
import web.onficina.repository.VeiculoRepository;
import web.onficina.service.ManutencaoService;

@Controller
@RequestMapping("/manutencao")
public class ManutencaoController {

    private static final Logger logger = LoggerFactory.getLogger(ManutencaoController.class);

    private final ManutencaoRepository manutencaoRepository;
    private final ManutencaoService manutencaoService;
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OficinaRepository oficinaRepository;

    public ManutencaoController(ManutencaoRepository manutencaoRepository, ManutencaoService manutencaoService,
            VeiculoRepository veiculoRepository, UsuarioRepository usuarioRepository,
            OficinaRepository oficinaRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.manutencaoService = manutencaoService;
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.oficinaRepository = oficinaRepository;
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model, Principal principal) {

        String email = principal.getName();
        Usuario proprietario = usuarioRepository.findByEmailIgnoreCase(email);

        if (proprietario == null) {
            throw new IllegalStateException("Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
        }

        List<Veiculo> veiculosDoUsuario = veiculoRepository.findAllByProprietarioId(proprietario.getId());

        List<Oficina> todasAsOficinas = oficinaRepository.findAll();

        model.addAttribute("manutencao", new Manutencao());
        model.addAttribute("veiculos", veiculosDoUsuario);
        model.addAttribute("oficinas", todasAsOficinas);

        return "manutencao/cadastrar :: formulario";
    }

    @PostMapping("/cadastrar")
    public String salvar(@Valid Manutencao manutencao, BindingResult result,
            Model model, RedirectAttributes redirectAttributes,
            Principal principal) {
        if (result.hasErrors()) {

            String email = principal.getName();
            Usuario proprietario = usuarioRepository.findByEmailIgnoreCase(email);

            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }

            model.addAttribute("veiculos", veiculoRepository.findAllByProprietarioId(proprietario.getId()));
            model.addAttribute("oficinas", oficinaRepository.findAll());

            return "manutencao/cadastrar :: formulario";

        }

        manutencaoService.salvar(manutencao);
        redirectAttributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Manutenção cadastrada com sucesso!",
                        TipoNotificaoSweetAlert2.SUCCESS, 4000));

        return "redirect:/manutencao/listar";
    }

    @HxRequest
    @GetMapping("/listar")
    public String listar(ManutencaoFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Manutencao> pagina = manutencaoRepository.pesquisar(filtro, pageable);
        PageWrapper<Manutencao> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("filtro", filtro);
        return "manutencao/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa(Model model) {

        List<Veiculo> todosVeiculos = veiculoRepository.findAll();
        List<Oficina> todasAsOficinas = oficinaRepository.findAll();
        model.addAttribute("veiculos", todosVeiculos);
        model.addAttribute("oficinas", todasAsOficinas);

        return "manutencao/pesquisar :: formulario";
    }

    @HxRequest
    @GetMapping("/pesquisar")
    public String pesquisar(ManutencaoFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Manutencao> pagina = manutencaoRepository.pesquisar(filtro, pageable);
        PageWrapper<Manutencao> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);

        return "manutencao/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model, Principal principal) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);
        if (manutencao != null) {
            String email = principal.getName();
            Usuario proprietario = usuarioRepository.findByEmailIgnoreCase(email);

            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }
            model.addAttribute("veiculos", veiculoRepository.findAllByProprietarioId(proprietario.getId()));
            model.addAttribute("oficinas", oficinaRepository.findAll());

            model.addAttribute("manutencao", manutencao);
            return "manutencao/alterar :: formulario";
        } else {
            model.addAttribute("mensagem", "Não existe uma manutenção com esse código");
            return "mensagem :: texto";
        }
    }

    @HxRequest
    @PostMapping("/alterar")
    public String alterar(@Valid Manutencao manutencao, BindingResult resultado,
            RedirectAttributes redirectAttributes, Principal principal, Model model) {
        if (resultado.hasErrors()) {
            String email = principal.getName();
            Usuario proprietario = usuarioRepository.findByEmailIgnoreCase(email);

            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }

            model.addAttribute("veiculos", veiculoRepository.findAllByProprietarioId(proprietario.getId()));
            model.addAttribute("oficinas", oficinaRepository.findAll());

            logger.info("A manutenção recebida para cadastrar não é válida.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "manutencao/alterar :: formulario";
        } else {

            manutencaoService.alterar(manutencao);
            redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Manutenção alterada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/manutencao/listar";
        }
    }

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
