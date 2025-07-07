package web.onficina.filter;

public class OficinaFilter {
    private String nome;
    private String telefone;
    private Double notaMedia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    @Override
    public String toString() {
        return "OficinaFilter [nome=" + nome + ", telefone=" + telefone + ", notaMedia=" + notaMedia + "]";
    }

}
