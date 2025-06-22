package web.onficina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.onficina.repository.PapelRepository;
import web.onficina.service.UsuarioService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import web.onficina.model.Papel;
import web.onficina.model.Usuario;
import web.onficina.notificacao.NotificacaoSweetAlert2;
import web.onficina.notificacao.TipoNotificaoSweetAlert2;

@Controller
public class UsuarioController {

	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	private PapelRepository papelRepository;
	private UsuarioService usuarioService;
	private PasswordEncoder passwordEncoder;

	public UsuarioController(PapelRepository papelRepository, UsuarioService cadastroUsuarioService,
			PasswordEncoder passwordEncoder) {
		this.papelRepository = papelRepository;
		this.usuarioService = cadastroUsuarioService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/cadastrar")
	public String abrirCadastroUsuario(Usuario usuario, Model model) {
		List<Papel> papeis = papelRepository.findAll();
		model.addAttribute("todosPapeis", papeis);
		return "usuario/cadastrar";
	}

<<<<<<< Updated upstream
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(
            @Valid Usuario usuario,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            logger.trace(">>>>>>>>>>>>>>>> Cadastro invalido");
            return "usuario/cadastro"; // volta com mensagens de erro
        }

        usuarioService.salvar(usuario);

        redirectAttributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
        return "redirect:/login";
    }
    

    @PostMapping("/login")
    public String loginUsuario(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBanco.isPresent() && usuario.getSenha().equals(usuarioBanco.get().getSenha())) {
            session.setAttribute("usuarioLogado", usuarioBanco.get());
            return "redirect:/painel";
        }

        model.addAttribute("erro", "Email ou senha inválidos.");
        return "usuario/login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão atual
        return "redirect:/login"; // Redireciona para a tela de login
    }
=======
	@PostMapping("/cadastrar")
	public String cadastrarNovoUsuario(@Valid Usuario usuario, BindingResult resultado, Model model,
			RedirectAttributes redirectAttributes) {
		if (resultado.hasErrors()) {
			logger.info("O usuario recebido para cadastrar não é válido.");
			logger.info("Erros encontrados:");
			for (FieldError erro : resultado.getFieldErrors()) {
				logger.info("{}", erro);
			}
			List<Papel> papeis = papelRepository.findAll();
			model.addAttribute("todosPapeis", papeis);
			return "usuario/cadastrar";
		} else {
			usuario.setAtivo(true);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			usuarioService.salvar(usuario);

			return "redirect:/login";
		}
	}

	/*
	 * @GetMapping("/cadastrosucesso")
	 * public String mostrarCadastroSucesso(String mensagem, Usuario usuario, Model
	 * model) {
	 * List<Papel> papeis = papelRepository.findAll();
	 * model.addAttribute("todosPapeis", papeis);
	 * if (mensagem != null && !mensagem.isEmpty()) {
	 * model.addAttribute("notificacao", new NotificacaoSweetAlert2(mensagem,
	 * TipoNotificaoSweetAlert2.SUCCESS, 4000));
	 * }
	 * return "usuario/cadastrar :: formulario";
	 * }
	 */
>>>>>>> Stashed changes

}
