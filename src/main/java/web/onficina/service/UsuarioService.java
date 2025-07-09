package web.onficina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Usuario;
import web.onficina.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void alterar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void remover(Long id) {
        usuarioRepository.deleteById(id);
    }

    public String findNomeByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Visitante";
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email);

        if (usuario != null) {
            return usuario.getNome();
        } else {
            return email;
        }
    }


    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

}
