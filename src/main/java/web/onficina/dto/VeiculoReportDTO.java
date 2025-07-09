package web.onficina.dto;

import java.util.List;
import java.util.stream.Collectors;
import web.onficina.model.Veiculo;

public class VeiculoReportDTO {

    private String placa;
    private String modelo;
    private String marca;
   private int ano; 
    private String cor;
    private List<ManutencaoReportDTO> manutencoes; 

public VeiculoReportDTO(Veiculo veiculo) {
    this.placa = veiculo.getPlaca();
    this.modelo = veiculo.getModelo();
    this.marca = veiculo.getMarca(); 
    this.ano = veiculo.getAno();
    this.cor = veiculo.getCor(); 
    this.manutencoes = veiculo.getManutencoes().stream()
                            .map(ManutencaoReportDTO::new)
                            .collect(Collectors.toList());
    // Log para debug
    System.out.println("Veículo: " + placa + ", Manutenções: " + manutencoes.size());
    manutencoes.forEach(m -> System.out.println("Manutenção: " + m.getData() + ", " + m.getServicoRealizado()));
}


    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public int getAno() {
        return ano;
    }

        public String getCor() {
        return cor;
    }


    public List<ManutencaoReportDTO> getManutencoes() {
        return manutencoes;
    }

    
}