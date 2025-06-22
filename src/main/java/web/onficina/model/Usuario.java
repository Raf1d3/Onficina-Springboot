package web.onficina.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.io.Serializable;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import web.onficina.service.EmailUsuarioUnicoService;
import web.onficina.validation.UniqueValueAttribute;

@Entity
@Table(name = "usuario")
@UniqueValueAttribute(attribute = "email", service = EmailUsuarioUnicoService.class, message = "Já existe um usuário com esse email cadastrado")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="geradorUsuario", sequenceName="usuario_id_seq", allocationSize=1)
    @GeneratedValue(generator="geradorUsuario", strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_papel")
    @NotNull(message = "O usuário deve escolher um tipo")
    private Papel papel;

    public Long getId() {
        return id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

    public void setPapel(Papel papel) {
		this.papel = papel;
	}

    public Papel getPapel() {
		return papel;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Usuario other = (Usuario) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "nome=" + nome + ", email=" + email + "\nativo: " + ativo;
    }

}
