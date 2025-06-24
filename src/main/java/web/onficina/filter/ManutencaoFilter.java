package web.onficina.filter;

import web.onficina.model.Manutencao.StatusManutencao;
import web.onficina.model.Manutencao.TipoManutencao;
import web.onficina.model.Manutencao.TipoServico;

public class ManutencaoFilter {
    private TipoManutencao tipoManutencao;
    private StatusManutencao statusManutencao;
    private TipoServico tipoServico;
    private Long veiculoId;
    private Long oficinaId;

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
