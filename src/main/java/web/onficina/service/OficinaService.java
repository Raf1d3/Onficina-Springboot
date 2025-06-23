package web.onficina.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.onficina.model.Oficina;
import web.onficina.repository.OficinaRepository;



@Service
@Transactional
public class OficinaService {

    private OficinaRepository oficinaRepository;

    public OficinaService(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    public void salvar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    public void alterar(Oficina oficina) {
        oficinaRepository.save(oficina);
    }

    public void remover(Long id) {
        oficinaRepository.deleteById(id);
    }
    


    
}
