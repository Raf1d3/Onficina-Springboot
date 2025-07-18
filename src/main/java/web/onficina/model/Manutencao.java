package web.onficina.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "manutencao")
public class Manutencao {
    public enum TipoManutencao {
        PREVENTIVA,
        CORRETIVA
    }

    public enum StatusManutencao {
        AGENDADA,
        EM_ANDAMENTO,
        FINALIZADA
    }

    public enum TipoServico {
        TROCA_OLEO,
        TROCA_PNEUS,
        ALINHAMENTO,
        BALANCEAMENTO,
        REPARO_FREIOS,
        TROCA_BATERIA,
        OUTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_inicio_manutencao")
    private LocalDate dataInicioManutencao;
    
    private String descricao;
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_manutencao", nullable = false)
    private TipoManutencao tipoManutencao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_manutencao", nullable = false)
    private StatusManutencao statusManutencao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servico", nullable = false)
    private TipoServico tipoServico;

    @Column(name = "descricao_outro_servico")
    private String descricaoOutroServico;

    @Column(name = "valor_servico", nullable = false)
    private BigDecimal valorServico;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_proxima_manutencao")
    private LocalDate dataProximaManutencao;

    @ManyToOne
    @JoinColumn(name = "oficina_id", nullable = true)
    private Oficina oficina;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataInicioManutencao() {
        return dataInicioManutencao;
    }

    public void setDataInicioManutencao(LocalDate dataInicioManutencao) {
        this.dataInicioManutencao = dataInicioManutencao;
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

    public String getDescricaoOutroServico() {
        return descricaoOutroServico;
    }

    public void setDescricaoOutroServico(String descricaoOutroServico) {
        this.descricaoOutroServico = descricaoOutroServico;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public LocalDate getDataProximaManutencao() {
        return dataProximaManutencao;
    }

    public void setDataProximaManutencao(LocalDate dataProximaManutencao) {
        this.dataProximaManutencao = dataProximaManutencao;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Manutencao other = (Manutencao) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Manutencao:\n" +
                "tipo=" + tipoManutencao + "\n" +
                "status=" + statusManutencao + "\n" +
                "servico=" + tipoServico + "\n" +
                "valor=" + valorServico + "\n" +
                "veiculo=" + (veiculo != null ? veiculo.getId() : "null") + "\n" +
                "oficina=" + (oficina != null ? oficina.getId() : "null");
    }

}