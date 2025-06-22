package web.onficina.filter;

import web.onficina.model.Manutencao.TipoManutencao;
import web.onficina.model.Manutencao.StatusManutencao;
import web.onficina.model.Manutencao.TipoServico;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ManutencaoFilter {

    private Long id; // 

    // Filtros para Veiculo associado
    private Long veiculoId;
    private String veiculoPlaca; 
    private String veiculoModelo;

    // Filtros para Oficina associada
    private Long oficinaId;
    private String oficinaNome; 

    // Filtros de Data
    private LocalDateTime dataInicioManutencaoApos; 
    private LocalDateTime dataInicioManutencaoAntes; 
    private LocalDateTime dataProximaManutencaoApos; 
    private LocalDateTime dataProximaManutencaoAntes; 

    // Filtros de Enum
    private TipoManutencao tipoManutencao;
    private StatusManutencao statusManutencao;
    private TipoServico tipoServico;

    // Filtros textuais
    private String descricao; 
    private String observacao;
    private String descricaoOutroServico;

    // Filtros de Valor
    private BigDecimal valorServicoMin;
    private BigDecimal valorServicoMax;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }

    public String getVeiculoModelo() {
        return veiculoModelo;
    }

    public void setVeiculoModelo(String veiculoModelo) {
        this.veiculoModelo = veiculoModelo;
    }

    public Long getOficinaId() {
        return oficinaId;
    }

    public void setOficinaId(Long oficinaId) {
        this.oficinaId = oficinaId;
    }

    public String getOficinaNome() {
        return oficinaNome;
    }

    public void setOficinaNome(String oficinaNome) {
        this.oficinaNome = oficinaNome;
    }

    public LocalDateTime getDataInicioManutencaoApos() {
        return dataInicioManutencaoApos;
    }

    public void setDataInicioManutencaoApos(LocalDateTime dataInicioManutencaoApos) {
        this.dataInicioManutencaoApos = dataInicioManutencaoApos;
    }

    public LocalDateTime getDataInicioManutencaoAntes() {
        return dataInicioManutencaoAntes;
    }

    public void setDataInicioManutencaoAntes(LocalDateTime dataInicioManutencaoAntes) {
        this.dataInicioManutencaoAntes = dataInicioManutencaoAntes;
    }

    public LocalDateTime getDataProximaManutencaoApos() {
        return dataProximaManutencaoApos;
    }

    public void setDataProximaManutencaoApos(LocalDateTime dataProximaManutencaoApos) {
        this.dataProximaManutencaoApos = dataProximaManutencaoApos;
    }

    public LocalDateTime getDataProximaManutencaoAntes() {
        return dataProximaManutencaoAntes;
    }

    public void setDataProximaManutencaoAntes(LocalDateTime dataProximaManutencaoAntes) {
        this.dataProximaManutencaoAntes = dataProximaManutencaoAntes;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getDescricaoOutroServico() {
        return descricaoOutroServico;
    }

    public void setDescricaoOutroServico(String descricaoOutroServico) {
        this.descricaoOutroServico = descricaoOutroServico;
    }

    public BigDecimal getValorServicoMin() {
        return valorServicoMin;
    }

    public void setValorServicoMin(BigDecimal valorServicoMin) {
        this.valorServicoMin = valorServicoMin;
    }

    public BigDecimal getValorServicoMax() {
        return valorServicoMax;
    }

    public void setValorServicoMax(BigDecimal valorServicoMax) {
        this.valorServicoMax = valorServicoMax;
    }

    @Override
    public String toString() {
        return "ManutencaoFilter{" +
                "id=" + id +
                ", veiculoId=" + veiculoId +
                ", veiculoPlaca='" + veiculoPlaca + '\'' +
                ", veiculoModelo='" + veiculoModelo + '\'' +
                ", oficinaId=" + oficinaId +
                ", oficinaNome='" + oficinaNome + '\'' +
                ", dataInicioManutencaoApos=" + dataInicioManutencaoApos +
                ", dataInicioManutencaoAntes=" + dataInicioManutencaoAntes +
                ", dataProximaManutencaoApos=" + dataProximaManutencaoApos +
                ", dataProximaManutencaoAntes=" + dataProximaManutencaoAntes +
                ", tipoManutencao=" + tipoManutencao +
                ", statusManutencao=" + statusManutencao +
                ", tipoServico=" + tipoServico +
                ", descricao='" + descricao + '\'' +
                ", observacao='" + observacao + '\'' +
                ", descricaoOutroServico='" + descricaoOutroServico + '\'' +
                ", valorServicoMin=" + valorServicoMin +
                ", valorServicoMax=" + valorServicoMax +
                '}';
    }
}