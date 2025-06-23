package web.onficina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.onficina.model.Oficina;
import web.onficina.repository.OficinaRepository;

@Service
public class CnpjOficinaUnicoServiceImpl implements CnpjOficinaUnicoService {
    
    @Autowired
    private OficinaRepository oficinaRepository;

    @Override
    public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("cnpj")) {
            throw new UnsupportedOperationException("A anotação deveria ser usada no atributo cnpj");
        }

        Oficina novaOficina = (Oficina) value;

        if (novaOficina.getCnpj() == null || novaOficina.getCnpj().isBlank()) {
            return true;
        }
        
        // 1. Busca uma oficina com este CNPJ no banco. O resultado será um objeto ou null.
        Oficina oficinaComEsseCnpj = oficinaRepository.findByCnpj(novaOficina.getCnpj());
        
        if (oficinaComEsseCnpj == null) {
            // Se o resultado é nulo, significa que o CNPJ não existe no banco. Portanto, é único.
            return true;
        }
        
        // 3. Se o código chegou até aqui, é porque uma oficina com esse CNPJ JÁ EXISTE.
        //    Agora, precisamos descobrir se é um caso de criação ou atualização.
        
        // Se a oficina sendo validada é NOVA (ID é 0, pois 'long' primitivo não pode ser nulo)
        if (novaOficina.getId() == 0) {
            // Estamos tentando criar uma nova oficina com um CNPJ que já existe. Não é único.
            return false;
        } else {  
            // É uma atualização. Precisamos verificar se o CNPJ encontrado pertence à própria oficina.
            if (oficinaComEsseCnpj.getId() == novaOficina.getId()) {
                // Sim, o CNPJ encontrado pertence à mesma oficina que estamos editando. É válido.
                return true;
            } else {
                // Não, o CNPJ encontrado pertence a outra oficina. Não é único.
                return false;
            }
        }
    }
}