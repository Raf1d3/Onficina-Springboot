package web.onficina.filter;

import java.math.BigDecimal;

import web.onficina.model.Manutencao.StatusManutencao;
import web.onficina.model.Manutencao.TipoManutencao;
import web.onficina.model.Manutencao.TipoServico;

public class ManutencaoFilter {
    private Long id;
    private TipoManutencao tipoManutencao;
    private StatusManutencao statusManutencao;
    private TipoServico tipoServico;
    private Long veiculoId;
    private Long oficinaId;
    private BigDecimal valorServico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public TipoManutencao getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(TipoManutencao tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public StatusManutencao getStatusManutencao() {
        return statusManutencao;
    }

    public void setStatusManutencao(StatusManutencao statusManutencao) {
        this.statusManutencao = statusManutencao;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }
    public Long getVeiculoId() { 
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) { 
        this.veiculoId = veiculoId;
    }

    public Long getOficinaId() { 
        return oficinaId;
    }

    public void setOficinaId(Long oficinaId) { 
        this.oficinaId = oficinaId;
    }

    @Override
    public String toString() {
        return "ManutencaoFilter [tipoManutencao=" + tipoManutencao + ", statusManutencao=" + statusManutencao
                + ", tipoServico=" + tipoServico + "]";
    }

}
