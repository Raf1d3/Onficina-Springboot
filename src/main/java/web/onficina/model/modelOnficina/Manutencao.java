package web.onficina.model.modelOnficina;

import java.time.LocalDateTime;

public class Manutencao {
    private long id;
    private LocalDateTime data;
    private String tipo;
    private String descricao;
    private LocalDateTime dataProximaManutencao;
    private Oficina oficina;
    private Veiculo veiculo;
    private Usuario proprietario;

}
