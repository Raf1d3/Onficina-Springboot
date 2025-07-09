package web.onficina.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.onficina.filter.AvaliacaoFilter;
import web.onficina.model.Avaliacao;
import web.onficina.model.Manutencao;
import web.onficina.model.Oficina;
import web.onficina.model.Status;
import web.onficina.model.Usuario;
import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.AvaliacaoRepository;
import web.onficina.repository.ManutencaoRepository;
import web.onficina.repository.OficinaRepository;
import web.onficina.repository.UsuarioRepository;
import web.onficina.repository.VeiculoRepository;
import web.onficina.service.AvaliacaoService;

@Controller
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OficinaRepository oficinaRepository;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository, AvaliacaoService avaliacaoService,
            VeiculoRepository veiculoRepository,
            UsuarioRepository usuarioRepository, OficinaRepository oficinaRepository,
            ManutencaoRepository manutencaoRepository) {
        this.oficinaRepository = oficinaRepository;
        this.usuarioRepository = usuarioRepository;
        this.veiculoRepository = veiculoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoService = avaliacaoService;
        this.manutencaoRepository = manutencaoRepository;
    }
    

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model, Principal principal) {

        String email = principal.getName();
        Usuario proprietario = usuarioRepository.findByEmailAndAtivo(email, true);

        if (proprietario == null) {
            throw new IllegalStateException("Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
        }

        List<Manutencao> manutencoesDoUsuario = manutencaoRepository
                .findAllByVeiculo_Proprietario_IdAndStatus(proprietario.getId(), Status.ATIVO);

        model.addAttribute("manutencoes", manutencoesDoUsuario);
        model.addAttribute("avaliacao", new Avaliacao());

        return "avaliacao/cadastrar :: formulario";
    }

    @PostMapping("/cadastrar")
    public String salvar(@Valid Avaliacao avaliacao, BindingResult result,
            Model model, RedirectAttributes redirectAttributes,
            Principal principal) {

        String email = principal.getName();
        Usuario proprietario = usuarioRepository.findByEmailAndAtivo(email, true);
        if (result.hasErrors()) {

            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }

            List<Manutencao> manutencoesDoUsuario = manutencaoRepository
                    .findAllByVeiculo_Proprietario_IdAndStatus(proprietario.getId(), Status.ATIVO);

            model.addAttribute("manutencoes", manutencoesDoUsuario);

            return "avaliacao/cadastrar :: formulario";

        }

        avaliacao.setProprietario(proprietario);

        avaliacaoService.salvar(avaliacao);
        redirectAttributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Avaliação cadastrada com sucesso!",
                        TipoNotificaoSweetAlert2.SUCCESS, 4000));

        return "redirect:/avaliacao/listar";
    }

    @HxRequest
    @GetMapping("/listar")
    public String listar(AvaliacaoFilter filtro, Model model,
            @PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Avaliacao> pagina = avaliacaoRepository.pesquisar(filtro, pageable);
        PageWrapper<Avaliacao> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);
        model.addAttribute("filtro", filtro);
        return "avaliacao/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/abrirpesquisar")
    public String abrirPaginaPesquisa(Model model, Principal principal) {

        String email = principal.getName();
        Usuario proprietario = usuarioRepository.findByEmailAndAtivo(email, true);

        if (proprietario == null) {
            throw new IllegalStateException("Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
        }

        List<Manutencao> manutencoesDoUsuario = manutencaoRepository.findAllByVeiculo_Proprietario_IdAndStatus(proprietario.getId(), Status.ATIVO);
        model.addAttribute("manutencao", manutencoesDoUsuario);
        model.addAttribute("proprietario", usuarioRepository.findAllByAtivo(true));


        return "avaliacao/pesquisar :: formulario";
    }

    @HxRequest
    @GetMapping("/pesquisar")
    public String pesquisar(AvaliacaoFilter filtro, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Avaliacao> pagina = avaliacaoRepository.pesquisar(filtro, pageable);
        PageWrapper<Avaliacao> paginaWrapper = new PageWrapper<>(pagina, request);

        model.addAttribute("pagina", paginaWrapper);

        return "avaliacao/listar :: tabela";
    }

    @HxRequest
    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable("id") Long id, Model model, Principal principal) {
        Avaliacao avaliacao = avaliacaoRepository.findByIdAndStatus(id, Status.ATIVO);
        if (avaliacao != null) {
            String email = principal.getName();
            Usuario proprietario = usuarioRepository.findByEmailAndAtivo(email, true);

            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }
            List<Manutencao> manutencoesDoUsuario = manutencaoRepository
                    .findAllByVeiculo_Proprietario_IdAndStatus(proprietario.getId(), Status.ATIVO);

            model.addAttribute("manutencoes", manutencoesDoUsuario);
            model.addAttribute("avaliacao", avaliacao);
            return "avaliacao/alterar :: formulario";   
        } else {
            model.addAttribute("mensagem", "Não existe uma avaliação com esse código");
            return "mensagem :: texto";
        }
    }

    @HxRequest
    @PostMapping("/alterar")
    public String alterar(@Valid Avaliacao avaliacao, BindingResult resultado,
            RedirectAttributes redirectAttributes, Principal principal, Model model) {

            String email = principal.getName();
            Usuario proprietario = usuarioRepository.findByEmailAndAtivo(email, true);

        if (resultado.hasErrors()) {


            if (proprietario == null) {
                throw new IllegalStateException(
                        "Usuário autenticado não pôde ser encontrado no banco de dados: " + email);
            }

            List<Manutencao> manutencoesDoUsuario = manutencaoRepository
                    .findAllByVeiculo_Proprietario_IdAndStatus(proprietario.getId(), Status.ATIVO);
            model.addAttribute("manutencoes", manutencoesDoUsuario);
            model.addAttribute("avaliacao", avaliacao);
            return "manutencao/alterar :: formulario";
        } else {

            avaliacao.setProprietario(proprietario);
            avaliacaoService.alterar(avaliacao);
            redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
                    "Avaliação alterada com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
            return "redirect:/avaliacao/listar";
        }
    }

    @HxRequest
    @HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
    @GetMapping("/remover/{id}")
    public String remover(@PathVariable("id") Long id, RedirectAttributes attributes) {
        avaliacaoService.remover(id);
        attributes.addFlashAttribute("notificacao",
                new NotificacaoSweetAlert2("Avaliação removida com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000)); // redirect
        return "redirect:/avaliacao/listar";
    }

}
