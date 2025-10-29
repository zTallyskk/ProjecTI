package controllers;

import play.*;
import play.mvc.*;
import security.Administrador;

import java.util.*;

import play.data.validation.*;

import models.Pessoa;
import models.Problema;
import models.Status;

public class Pessoas extends Controller {

	@Administrador
	public static void form() {
		List<Problema> problemas = Problema.findAll();
		render(problemas);
	}

	public static void detalhar(Pessoa pessoa) {
		render(pessoa);
	}

	public static void listar(String termo) {
		List<Pessoa> pessoas = null;
		if (termo == null) {
			pessoas = Pessoa.find("status = ?1", Status.PENDENTE).fetch();
		} else {
			pessoas = Pessoa.find("(lower(nome) like ?1 " + 
			"or lower(email) like ?1) and status = ?2",
			"%" + termo.toLowerCase() + "%",
			Status.PENDENTE).fetch();
		}
		render(pessoas, termo);
	}

	public static void editar(Long id) {
		Pessoa p = Pessoa.findById(id);
		List<Problema> problemas = Problema.findAll();

		renderTemplate("Pessoas/form.html", p, problemas);
	}
	
	@Administrador
	public static void salvar(@Valid Pessoa pessoa) {
		if(validation.hasErrors()) {
			params.flash();
			validation.keep();
			form();
		}
		pessoa.nome = pessoa.nome.toUpperCase();		
		pessoa.email = pessoa.email.toLowerCase();
		
		flash.success(pessoa.nome + " foi cadastrada com sucesso.");
		pessoa.save();
		detalhar(pessoa);
	}

	public static void remover(Long id) {
		Pessoa pessoa = Pessoa.findById(id);
		pessoa.status = Status.RESOLVIDO;
		pessoa.save();
		listar(null);
	}

}
