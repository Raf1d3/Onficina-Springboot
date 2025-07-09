package web.onficina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Status;
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
        Usuario usuario = usuarioRepository.findByIdAndAtivo(id, true);
        if (usuario == null) {
            throw new RuntimeException("Remoção do usuario com codigo inválido");
        } else {
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        }
    }

    public String findNomeByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Visitante";
        }

        Usuario usuario = usuarioRepository.findByEmailAndAtivo(email, true);

        if (usuario != null) {
            return usuario.getNome();
        } else {
            return email;
        }
    }


    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmailAndAtivo(email, true);
    }

}
