package web.onficina.filter;

import java.time.LocalDateTime;

public class AvaliacaoFilter {

    private Long id;
    private Long oficinaId;
    private Long veiculoId;
    private Long proprietarioId;
    private Long manutencaoId;
    private Double nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOficinaId() {
        return oficinaId;
    }
    public void setOficinaId(Long oficinaId) {
        this.oficinaId = oficinaId;
    }
    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
    public Long getVeiculoId() {
        return veiculoId;
    }
    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
    public Long getProprietarioId() {
        return proprietarioId;
    }
    public void setProprietarioId(Long proprietarioId) {
        this.proprietarioId = proprietarioId;
    }
    public Long getManutencaoId() {
        return manutencaoId;
    }
    public void setManutencaoId(Long manutencaoId) {
        this.manutencaoId = manutencaoId;
    }
    public Double getNota() {
        return nota;
    }
    public void setNota(Double nota) {
        this.nota = nota;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    @Override
    public String toString() {
        return "AvaliacaoFilter [veiculoId=" + veiculoId + ", proprietarioId=" + proprietarioId + ", manutencaoId="
                + manutencaoId + ", nota=" + nota + ", comentario=" + comentario + "]";
    }

}
