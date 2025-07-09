package web.onficina.filter;

public class UsuarioFilter {
    
    private Long id;
    private String nome;
    private String email;
    private Long papelId;
    private Boolean ativo;

    // Getters e Setters para todos os campos...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPapelId() {
        return papelId;
    }

    public void setPapelId(Long papelId) {
        this.papelId = papelId;
    }
}