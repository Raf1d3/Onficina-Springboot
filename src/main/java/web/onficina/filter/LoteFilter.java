package web.onficina.filter;

import java.time.LocalDate;

import web.onficina.model.Vacina;

public class LoteFilter {

    private Long codigo;
    private LocalDate inicioValidade;
    private LocalDate fimValidade;
    private Integer minimoDosesLote;
    private Integer maximoDosesLote;
    private Integer minimoDosesAtual;
    private Integer maximoDosesAtual;
    private Vacina vacina;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getInicioValidade() {
        return inicioValidade;
    }

    public void setInicioValidade(LocalDate inicioValidade) {
        this.inicioValidade = inicioValidade;
    }

    public LocalDate getFimValidade() {
        return fimValidade;
    }

    public void setFimValidade(LocalDate fimValidade) {
        this.fimValidade = fimValidade;
    }

    public Integer getMinimoDosesLote() {
        return minimoDosesLote;
    }

    public void setMinimoDosesLote(Integer minimoDosesLote) {
        this.minimoDosesLote = minimoDosesLote;
    }

    public Integer getMaximoDosesLote() {
        return maximoDosesLote;
    }

    public void setMaximoDosesLote(Integer maximoDosesLote) {
        this.maximoDosesLote = maximoDosesLote;
    }

    public Integer getMinimoDosesAtual() {
        return minimoDosesAtual;
    }

    public void setMinimoDosesAtual(Integer minimoDosesAtual) {
        this.minimoDosesAtual = minimoDosesAtual;
    }

    public Integer getMaximoDosesAtual() {
        return maximoDosesAtual;
    }

    public void setMaximoDosesAtual(Integer maximoDosesAtual) {
        this.maximoDosesAtual = maximoDosesAtual;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    @Override
    public String toString() {
        return "LoteFilter [codigo=" + codigo + ", inicioValidade=" + inicioValidade + ", fimValidade=" + fimValidade
                + ", minimoDosesLote=" + minimoDosesLote + ", maximoDosesLote=" + maximoDosesLote
                + ", minimoDosesAtual=" + minimoDosesAtual + ", maximoDosesAtual=" + maximoDosesAtual + ", vacina="
                + vacina + "]";
    }

}
