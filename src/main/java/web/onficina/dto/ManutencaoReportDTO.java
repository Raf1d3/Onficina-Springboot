package web.onficina.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import web.onficina.model.Manutencao;

public class ManutencaoReportDTO {

    private LocalDate data;
    private String servicoRealizado;
    private BigDecimal valor;
    private String nomeOficina;

    public ManutencaoReportDTO(Manutencao manutencao) {
        this.data = manutencao.getDataInicioManutencao();
        this.servicoRealizado = manutencao.getTipoServico().toString();
        this.valor = manutencao.getValorServico();
        if (manutencao.getOficina() != null) {
            this.nomeOficina = manutencao.getOficina().getNome();
        }
    }

    // Getters
    public LocalDate getData() {
        return data;
    }

    public String getServicoRealizado() {
        return servicoRealizado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getNomeOficina() {
        return nomeOficina;
    }
}