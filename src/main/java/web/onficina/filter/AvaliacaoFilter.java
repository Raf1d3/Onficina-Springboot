package web.onficina.filter;

public class AvaliacaoFilter {
    private Long veiculoId;
    private Long proprietarioId;
    private Long manutencaoId;
    private Integer nota;
    private String comentario;
    
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
    public Integer getNota() {
        return nota;
    }
    public void setNota(Integer nota) {
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
