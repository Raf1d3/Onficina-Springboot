package web.onficina.model;

import java.util.Objects;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import web.onficina.service.CnpjOficinaUnicoService;
import web.onficina.validation.UniqueValueAttribute;

@Entity
@Table(name = "oficina")
@UniqueValueAttribute(attribute = "cnpj", service = CnpjOficinaUnicoService.class, message = "Já existe uma oficina com esse CNPJ cadastrado")
public class Oficina {

    @Id
    @SequenceGenerator(name="geradorOficina", sequenceName="oficina_id_seq", allocationSize=1)
    @GeneratedValue(generator="geradorOficina", strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @Column(name = "nota_media")
    private Double notaMedia = 0.0;

    @OneToMany(mappedBy = "oficina", fetch = FetchType.LAZY)
    private java.util.List<Avaliacao> avaliacoes = new java.util.ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

     public java.util.List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(java.util.List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Oficina other = (Oficina) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Oficina:\n" +
               "nome=" + nome + "\n" +
               "cnpj=" + cnpj + "\n" +
               "endereco=" + endereco + "\n" +
               "telefone=" + telefone + "\n" +
               "notaMedia=" + notaMedia + "\n";
    }

}
