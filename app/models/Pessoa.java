package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.Email;

@Entity
public class Pessoa extends Model {

	@Required(message = "O campo Nome é obrigatório.")
	public String nome;
	
	@Required(message = "O campo E-mail é obrigatório.")
    @Email(message = "O formato do e-mail é inválido. Ex: nome@dominio.com")
	public String email;
	
	@Required(message = "O campo Telefone é obrigatório.")
	public Integer tel;

	@Enumerated(EnumType.STRING)
	public Perfil perfil;
	
	@OneToOne
	public Usuario usuario;
	
	public String textoProblem;
	
	@ManyToOne
	public Problema problema;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Pessoa() {
		this.status = Status.PENDENTE;
		this.perfil = perfil.PESSOA;
	}
}
