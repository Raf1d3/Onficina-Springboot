package web.onficina.controller;

import java.security.Principal;
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
import jakarta.validation.Valid;
import web.onficina.filter.VeiculoFilter;
import web.onficina.model.Status;
import web.onficina.model.Usuario;
import web.onficina.model.Veiculo;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.UsuarioRepository;
import web.onficina.repository.VeiculoRepository;
import web.onficina.service.VeiculoService;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoRepository veiculoRepository;
    private final VeiculoService veiculoService;
    private final UsuarioRepository usuarioRepository;

    public VeiculoController(VeiculoRepository veiculoRepository, VeiculoService veiculoService,
            UsuarioRepository usuarioRepository) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoService = veiculoService;
        this.usuarioRepository = usuarioRepository;
    }

    @HxRequest
    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("veiculo", new Veiculo());

        return "veiculo/cadastrar :: formulario";
    }

    @HxRequest
    @PostMapping("/cadastrar")
    public String salvar(@Valid Veiculo veiculo, BindingResult result,
            Model model, RedirectAttributes redirectAttributes,
            Principal principal) {

        if (result.hasErrors()) {
            return "veiculo/cadastrar :: formulario";
        }

        String email = principal.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmailAndAtivo(email, true);
        if (usuarioLogado == null) {
            throw new IllegalStateException("Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
        }

        veiculo.setProprietario(usuarioLogado);

        veiculoService.salvar(veiculo);
        redirectAttributes.addAttribute("mensagem", "Veículo cadastrado com sucesso!");
        return "redirect:/veiculo/listar";
    }

    @HxRequest
    @GetMapping("/listar")
    public String listar(VeiculoFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Veiculo> pagina = veiculoRepository.pesquisar(filtro, pageable);
        PageWrapper<Veiculo> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        return "veiculo/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa() {
        return "veiculo/pesquisar :: formulario";
    }

    @HxRequest
    @GetMapping("/pesquisar")
    public String pesquisar(VeiculoFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Veiculo> pagina = veiculoRepository.pesquisar(filtro, pageable);
        PageWrapper<Veiculo> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        return "veiculo/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findByIdAndStatus(id, Status.ATIVO);
        if (veiculo != null) {
            model.addAttribute("veiculo", veiculo);
            return "veiculo/alterar :: formulario";
        } else {
            model.addAttribute("mensagem", "Não existe um veiculo com esse código");
            return "mensagem :: texto";
        }
    }

    @HxRequest
    @PostMapping("/alterar")
    public String alterar(@Valid Veiculo veiculo, BindingResult resultado,
            RedirectAttributes redirectAttributes,
            Principal principal) {

        if (resultado.hasErrors()) {
            logger.info("O veiculo recebido para cadastrar não é válido.");
            logger.info("Erros encontrados:");
            for (FieldError erro : resultado.getFieldErrors()) {
                logger.info("{}", erro);
            }
            for (ObjectError erro : resultado.getGlobalErrors()) {
                logger.info("{}", erro);
            }
            return "veiculo/alterar :: formulario";
        } else {

            String email = principal.getName();
            Usuario usuarioLogado = usuarioRepository.findByEmailAndAtivo(email, true);
            if (usuarioLogado == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }

            veiculo.setProprietario(usuarioLogado);

            veiculoService.alterar(veiculo);
            redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Veiculo alterado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/veiculo/listar";
        }
    }

    @HxRequest
    @HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        veiculoService.remover(id);
        attributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Veiculo removido com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
        return "redirect:/veiculo/listar";
    }


    //Recarregar
    @GetMapping("/listar")
    public String listar2(VeiculoFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Veiculo> pagina = veiculoRepository.pesquisar(filtro, pageable);
        PageWrapper<Veiculo> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        return "veiculo/listar";
    }



}
