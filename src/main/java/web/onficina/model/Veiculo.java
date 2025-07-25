package web.onficina.model;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import web.onficina.service.PlacaVeiculoUnicoService;
import web.onficina.validation.UniqueValueAttribute;

@Entity
@Table(name = "veiculo")
@DynamicUpdate
@UniqueValueAttribute(attribute = "placa", service = PlacaVeiculoUnicoService.class, message = "Já existe um Veiculo com essa placa cadastrado")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String placa;

    private String marca;
    private String modelo;
    private int ano;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario proprietario;

    private String cor;

    @OneToMany(mappedBy = "veiculo", fetch = FetchType.EAGER)
    private java.util.List<Manutencao> manutencoes = new java.util.ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Usuario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Usuario proprietario) {
        this.proprietario = proprietario;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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

     public java.util.List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(java.util.List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Veiculo other = (Veiculo) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Veiculo:\n" +
               "placa=" + placa + "\n" +
               "modelo=" + modelo + "\n" +
               "marca=" + marca + "\n" +
               "cor=" + cor + "\n" +
               "ano=" + ano + "\n" +
               "proprietario=" + proprietario.getId();
    }
}
