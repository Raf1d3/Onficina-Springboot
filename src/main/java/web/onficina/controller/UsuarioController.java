package web.onficina.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.onficina.filter.UsuarioFilter;
import web.onficina.model.Papel;
import web.onficina.model.Status;
import web.onficina.model.Usuario;
import web.onficina.model.Veiculo;
import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;
import web.onficina.pagination.PageWrapper;
import web.onficina.repository.PapelRepository;
import web.onficina.repository.UsuarioRepository;
import web.onficina.repository.VeiculoRepository;
import web.onficina.service.UsuarioService;

@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	private PapelRepository papelRepository;
	private UsuarioService usuarioService;
	private UsuarioRepository usuarioRepository;
	private VeiculoRepository veiculoRepository;
	private PasswordEncoder passwordEncoder;

	public UsuarioController(PapelRepository papelRepository, UsuarioService cadastroUsuarioService,
			UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, VeiculoRepository veiculoRepository) {
		this.papelRepository = papelRepository;
		this.usuarioService = cadastroUsuarioService;
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
		this.veiculoRepository = veiculoRepository;
	}

	@GetMapping("/cadastrar")
	public String abrirCadastroUsuario(Usuario usuario, Model model) {
		return "usuario/cadastrar";

	}

	@HxRequest
	@GetMapping("/cadastrar")
	public String abrirCadastroUsuarioHtmx(Usuario usuario, Model model) {
		return "usuario/cadastrar :: formulario";

	}

	@PostMapping("/cadastrar")
	public String cadastrarNovoUsuario(@Valid Usuario usuario, BindingResult resultado, Model model,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			return "usuario/cadastrar";
		} else {
			Papel papelPadrao = papelRepository.findByNome("cliente");
			if (papelPadrao == null) {
				throw new RuntimeException("Papel cliente não existe");
			} else {
				usuario.setPapel(papelPadrao);
				usuario.setAtivo(true);
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
				usuarioService.salvar(usuario);

				if (principal != null) {
					redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
					return "redirect:/usuario/listar";
				} else {
					return "redirect:/login";
				}
			}
		}
	}

	@HxRequest
	@GetMapping("/usuario/listar")
	public String listar(UsuarioFilter filtro, Model model,
			@PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			HttpServletRequest request) {

		Page<Usuario> pagina = usuarioRepository.pesquisar(filtro, pageable);
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(pagina, request);

		model.addAttribute("pagina", paginaWrapper);
		return "usuario/listar :: tabela";
	}

	@GetMapping("/usuario/listar")
	public String listar2(UsuarioFilter filtro, Model model,
			@PageableDefault(size = 7) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			HttpServletRequest request) {

		Page<Usuario> pagina = usuarioRepository.pesquisar(filtro, pageable);
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(pagina, request);

		model.addAttribute("pagina", paginaWrapper);
		return "usuario/listar";
	}

	@HxRequest
	@GetMapping("/usuario/abrirpesquisar")
	public String abrirPaginaPesquisa() {
		return "usuario/pesquisar :: formulario";
	}

	@HxRequest
	@GetMapping("/usuario/pesquisar")
	public String pesquisar(UsuarioFilter filtro, Model model,
			@PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
			HttpServletRequest request) {

		Page<Usuario> pagina = usuarioRepository.pesquisar(filtro, pageable);
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(pagina, request);

		model.addAttribute("pagina", paginaWrapper);
		return "usuario/listar :: tabela";
	}

	@HxRequest
	@GetMapping("/usuario/alterar/{id}")
	public String abrirAlterar(@PathVariable("id") Long id, Model model) {
		Usuario usuario = usuarioRepository.findByIdAndAtivo(id, true);
		model.addAttribute("todosPapeis", papelRepository.findAll());
		if (usuario != null) {
			model.addAttribute("usuario", usuario);
			return "usuario/alterar :: formulario";
		} else {
			model.addAttribute("mensagem", "Não existe um usuario com esse código");
			return "mensagem :: texto";
		}
	}

	@HxRequest
	@PostMapping("/usuario/alterar")
	public String alterar(@Valid Usuario usuario, BindingResult resultado,
			RedirectAttributes redirectAttributes, Model model, Principal principal) {

		if (resultado.hasErrors()) {
			logger.info("O Usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			for (ObjectError erro : resultado.getGlobalErrors()) {
				logger.info("{}", erro);
			}

			model.addAttribute("todosPapeis", papelRepository.findAll());
			if (usuario != null) {
				model.addAttribute("usuario", usuario);
				return "usuario/alterar :: formulario";
			} else {
				model.addAttribute("mensagem", "Não existe um usuario com esse código");
				return "mensagem :: texto";
			}
		} else {
			Usuario usuarioLogado = usuarioRepository.findByEmail(principal.getName());

			if (usuarioLogado.getPapel().getNome().equals("admin")) {
				usuarioService.alterar(usuario);
				redirectAttributes.addFlashAttribute("notificacao", new NotificacaoSweetAlert2(
						"Usuario alterado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
				return "redirect:/usuario/listar";
			} else {
				usuario.setPapel(usuarioLogado.getPapel());
				usuario.setAtivo(usuarioLogado.isAtivo());
				usuarioService.alterar(usuario);

				return "redirect:/painel";
			}
		}
	}

	@GetMapping("/teste")
	public String teste(Model model, Principal principal){
		Usuario usuarioLogado = usuarioRepository.findByEmail(principal.getName());
		List<Veiculo> veiculodousuario = veiculoRepository.findAllByProprietarioIdAndStatus(usuarioLogado.getId(), Status.ATIVO);
		model.addAttribute("usuario", usuarioLogado);
		model.addAttribute("veiculo", veiculodousuario);
		return "/usuario/perfil";
	}

	@HxRequest
	@HxLocation(path = "/mensagem", target = "#main", swap = "outerHTML")
	@GetMapping("/usuario/remover/{id}")
	public String remover(@PathVariable("id") Long id, RedirectAttributes attributes,
			Principal principal) {

		String email = principal.getName();
		Usuario usuarioDel = usuarioRepository.findById(id).get();
		if (usuarioDel.getEmail().equals(email)) {
			attributes.addFlashAttribute("notificacao",
					new NotificacaoSweetAlert2("Um Usuario Conectado não pode ser desativado!",
							TipoNotificaoSweetAlert2.SUCCESS, 4000));
		} else if (usuarioDel.isAtivo()) {
			usuarioService.remover(id);
			attributes.addFlashAttribute("notificacao",
					new NotificacaoSweetAlert2("Usuario desativado com sucesso!", TipoNotificaoSweetAlert2.SUCCESS,
							4000));
		} else {
			attributes.addFlashAttribute("notificacao",
					new NotificacaoSweetAlert2("Usuario já está desativado!", TipoNotificaoSweetAlert2.SUCCESS, 4000));
		}

		return "redirect:/usuario/listar";
	}

}