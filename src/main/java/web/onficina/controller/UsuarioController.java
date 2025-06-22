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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import web.onficina.model.Papel;
import web.onficina.model.Usuario;

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

}
