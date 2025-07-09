package web.onficina.dto;

import web.onficina.model.Avaliacao;

public class AvaliacaoReportDTO {

    private Integer nota;
    private String comentario;
    private String nomeCliente; 

    public AvaliacaoReportDTO(Avaliacao avaliacao) {
        this.nota = avaliacao.getNota();
        this.comentario = avaliacao.getComentario();
        this.nomeCliente = avaliacao.getProprietario().getNome();
    }

    // Getters
    public Integer getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
}