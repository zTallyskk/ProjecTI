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

	@Required
	public String nome;
	
	@Email
	@Required
	public String email;
	
	public String login;
	
	public String senha;
	
	@Required
	public Integer tel;

	@OneToOne
	public Usuario usuario;
	
	public String textoProblem;
	
	@ManyToOne
	public Problema problema;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Pessoa() {
		this.status = Status.PENDENTE;
	}
}
