package web.onficina.service;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.onficina.model.Usuario;
import web.onficina.repository.UsuarioRepository;

@Service
public class EmailUsuarioUnicoServiceImpl implements EmailUsuarioUnicoService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("email")) {
            throw new UnsupportedOperationException("A anotação deveria ser usada no atributo email");
        }

        Usuario novoUsuario = (Usuario) value;

        //A validacao se foi preenchido um nomeUsuario nao eh obrigacao dessa verificacao
        if (novoUsuario.getEmail() == null || novoUsuario.getEmail().isBlank()) {
            return true;
        }
        
        //Busca um usuario com esse nomeUsuario email
        Usuario usuarioComEsseEmail = usuarioRepository.findByEmail(novoUsuario.getEmail());
        
        // Se não existe um usuário com esse email, o valor é único.
        if (usuarioComEsseEmail == null) {
            return true;
        } else {  // Existe um usuário com esse email.
            // Tentativa de criar um NOVO usuário com um email que já existe.
            if (novoUsuario.getId() == null || novoUsuario.getId() == 0) {
                return false;
            } else {  // Tentativa de ATUALIZAR um usuário já existente.
                Usuario usuarioAntigo = usuarioRepository.findById(novoUsuario.getId())
                    .orElseThrow(() -> new InvalidParameterException("O código do usuário a validar não existe."));
                // Se o usuário encontrado com o mesmo email é o próprio usuário que está sendo atualizado,
                // então está tudo bem (ele pode manter o próprio email).
                if (usuarioComEsseEmail.equals(usuarioAntigo)) {
                    return true;
                }
                
                // Se não, é porque estão tentando alterar o email para um que já pertence a OUTRO usuário.
                return false;
            }
        }
    }
}