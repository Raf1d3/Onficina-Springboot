package web.onficina.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import web.onficina.model.modelOnficina.Usuario;
import web.onficina.repository.UsuarioRepository;
import web.onficina.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUsuario(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioBanco = usuarioOptional.get();

            if (usuario.getSenha().equals(usuarioBanco.getSenha())) {
                // Login bem-sucedido
                model.addAttribute("usuarioLogado", usuarioBanco);
                logger.trace(">>>>>>>>>>>>>>>> usuario Logado");
                session.setAttribute("usuarioLogado", usuarioBanco);
                return "redirect:/painel";
            } else {
                // Senha incorreta
                model.addAttribute("erro", "Senha incorreta!");
                logger.trace(">>>>>>>>>>>>>>>> Senha incorreta!");
                return "usuario/login";
            }
        } else {
            // Usuário não encontrado
            model.addAttribute("erro", "Usuário não encontrado!");
            logger.trace(">>>>>>>>>>>>>>>> Usuário não encontrado!");
            return "usuario/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão atual
        return "redirect:/login"; // Redireciona para a tela de login
    }

}
