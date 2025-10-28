package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pessoa;
import models.Atendimento;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class AtendimentosMembros extends Controller {
	
	public static void form(Long id) {
		Atendimento atendimentos = Atendimento.findById(id);
		List<Pessoa> pessoas = Pessoa.findAll();
		render(atendimentos, pessoas);
	}
	
	public static void salvar(Long pessoaId, Long atendimentoId) {
		Pessoa pessoa = Pessoa.findById(pessoaId);
		Atendimento atendimento = Atendimento.findById(atendimentoId);
		
		if (atendimento.membros == null) {
			atendimento.membros = new ArrayList<Pessoa>();
		}
		
		if (atendimento.membros.contains(pessoa)) {
			flash.error("Essa pessoa já esta neste serviço!");
			form(atendimentoId);
		}
		
		atendimento.membros.add(pessoa);
		atendimento.save();
		form(atendimentoId);
	}
	
	public static void remover(Long atendimentoId, Long pessoaId) {
		Pessoa pessoa = Pessoa.findById(pessoaId);
		Atendimento atendimento = Atendimento.findById(atendimentoId);
		
		if (!atendimento.membros.contains(pessoa)) {
			flash.error("Essa pessoa não é membro da equipe!");
			form(atendimentoId);
		}
		
		atendimento.membros.remove(pessoa);
		atendimento.save();
		flash.success("Pessoa removida com sucesso!");
		form(atendimentoId);
	}
	public static void exibirDetalhes(Long atendimentoId) {
        // 1. OBRIGATÓRIO: Buscar o Atendimento
        Atendimento atendimento = Atendimento.findById(atendimentoId);

        // **Tratamento de Nulo:** O objeto pode não ter sido encontrado.
        if (atendimento == null) {
            notFound("Atendimento não encontrado!"); // Ou redirecione para uma lista
        }

        // 2. OBRIGATÓRIO: Buscar a lista de Pessoas (para o <select>)
        List<Pessoa> pessoas = Pessoa.findAll();

        // 3. Renderizar, passando as duas variáveis
        render(atendimento, pessoas);
    }

}
