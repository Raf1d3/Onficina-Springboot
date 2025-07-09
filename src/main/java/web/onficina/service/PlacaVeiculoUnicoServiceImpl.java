package web.onficina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.onficina.model.Veiculo;
import web.onficina.repository.VeiculoRepository;

@Service
public class PlacaVeiculoUnicoServiceImpl implements PlacaVeiculoUnicoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
        // 1. Verifica se a anotação está no campo 'placa'
        if (!fieldName.equals("placa")) {
            throw new UnsupportedOperationException("A anotação deveria ser usada no atributo placa");
        }

        // 2. Converte o objeto para o tipo Veiculo
        Veiculo novoVeiculo = (Veiculo) value;

        // Se a placa não foi preenchida, a validação de unicidade não se aplica aqui.
        if (novoVeiculo.getPlaca() == null || novoVeiculo.getPlaca().isBlank()) {
            return true;
        }
        
        // 3. Busca um veículo com esta placa no banco (ignorando maiúsculas/minúsculas)
        Veiculo veiculoComEssaPlaca = veiculoRepository.findByPlacaIgnoreCase(novoVeiculo.getPlaca());
        
        // 4. Se não existe um veículo com essa placa, o valor é único.
        if (veiculoComEssaPlaca == null) {
            return true;
        } else {  // Existe um veículo com essa placa.
            
            // 5. Diferencia entre CRIAR e ATUALIZAR
            
            // Se o veículo sendo validado é NOVO (ID é 0), então não pode usar uma placa que já existe.
            if (novoVeiculo.getId() == 0) { // Para 'long' primitivo, o padrão é 0
                return false;
            } else {  // O veículo sendo validado já existe (é uma atualização).
                
                // Comparamos o ID do veículo encontrado com o ID do veículo que está sendo atualizado.
                // Se os IDs forem os mesmos, significa que o veículo pode manter sua própria placa.
                if (veiculoComEssaPlaca.getId() == novoVeiculo.getId()) {
                    return true;
                }
                
                // Se não, é porque estão tentando alterar a placa para uma que já pertence a OUTRO veículo.
                return false;
            }
        }
    }
}