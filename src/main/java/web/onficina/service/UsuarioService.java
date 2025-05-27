package web.onficina.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.onficina.model.Usuario;
import web.onficina.repository.UsuarioRepository;


@Service
@Transactional
public class UsuarioService {


    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void alterar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void remover(Long codigo) {
        usuarioRepository.deleteById(codigo);
    }

    
}
