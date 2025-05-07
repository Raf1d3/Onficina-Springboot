package web.onficina.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "lote")
@DynamicUpdate
public class Lote implements Serializable {

	private static final long serialVersionUID = -3935828642122652510L;
	
	@Id
	@SequenceGenerator(name="gerador4", sequenceName="lote_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador4", strategy = GenerationType.SEQUENCE)
	private Long codigo;
	private LocalDate validade;
	@Column(name = "nro_doses_do_lote")
	private int nroDosesDoLote;
	@Column(name = "nro_doses_atual")
	private int nroDosesAtual;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_vacina")
	private Vacina vacina;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public int getNroDosesDoLote() {
		return nroDosesDoLote;
	}

	public void setNroDosesDoLote(int nroDosesDoLote) {
		this.nroDosesDoLote = nroDosesDoLote;
	}

	public int getNroDosesAtual() {
		return nroDosesAtual;
	}

	public void setNroDosesAtual(int nroDosesAtual) {
		this.nroDosesAtual = nroDosesAtual;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "codigo: " + codigo + "\nvalidade: " + validade + "\nnroDosesDoLote: " + nroDosesDoLote
				+ "\nnroDosesAtual: " + nroDosesAtual;
	}

}
