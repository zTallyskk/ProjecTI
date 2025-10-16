package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pessoa;
import models.Atendimento;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Atendimentos extends Controller {
	
	public static void listar() {
		List<Atendimento> atendimentos = Atendimento.findAll();
		render(atendimentos);
	}
}
