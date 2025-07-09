package web.onficina.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import web.onficina.model.Status;
import web.onficina.model.Usuario;
import web.onficina.model.Veiculo;
import web.onficina.repository.UsuarioRepository;
import web.onficina.repository.VeiculoRepository;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;
    
    /**
     * Método privado para carregar os dados do perfil, evitando duplicação de código.
     */
    private void loadProfileData(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByEmailAndAtivo(principal.getName(), true);
        List<Veiculo> veiculos = veiculoRepository.findAllByProprietarioIdAndStatus(usuario.getId(), Status.ATIVO);
        model.addAttribute("usuario", usuario);
        model.addAttribute("veiculos", veiculos);
    }

    /**
     * Lida com o carregamento completo da página (navegação direta ou F5).
     */
    @GetMapping
    public String showProfile(Model model, Principal principal) {
        loadProfileData(model, principal);
        return "usuario/perfil";
    }

    /**
     * Lida com as requisições HTMX, atualizando apenas os fragmentos necessários.
     */
    @HxRequest
    @GetMapping
    public View showProfileHtmx(Model model, Principal principal) {
        loadProfileData(model, principal);
        // Retorna o fragmento do conteúdo principal e também o do cabeçalho
        return FragmentsRendering.with("usuario/perfil :: conteudo")
            .fragment("layout/fragments/header :: usuariologinlogout")
            .build();
    }
}