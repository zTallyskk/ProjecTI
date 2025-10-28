package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pessoa;
import models.Atendimento;
import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class AtendimentosMembros extends Controller {

    // Método 'form' (mantido com a lógica de correção de NullPointerException)
    public static void form(Long id) {
        Atendimento atendimento = null; 
        
        if (id != null) {
            atendimento = Atendimento.findById(id); 
        }
        
        if (atendimento == null) {
            if (id != null) {
                 notFound("Atendimento não encontrado com ID: " + id);
            } else {
                 atendimento = new Atendimento();
            }
        }
        
        List<Pessoa> pessoas = Pessoa.findAll();
        
        render(atendimento, pessoas);
    }
    
    // Método 'salvar' ajustado para redirecionar para 'form' em caso de erro grave
    public static void salvar(Long pessoaId, Long atendimentoId) {

        // --- Tratamento para evitar IllegalArgumentException (ID nulo) ---
        if (pessoaId == null || atendimentoId == null) {
            flash.error("IDs de Pessoa e Atendimento são obrigatórios. Falha ao adicionar membro.");
            
            // Se o atendimentoId for nulo, não podemos chamar form(atendimentoId). 
            // Neste caso, você deve redirecionar para uma página segura (ex: lista principal). 
            // Se você não tem uma lista, você precisará criar um método para isso.
            // Por enquanto, vamos parar a execução aqui e presumir que a requisição
            // veio de uma página onde o ID deveria estar.
            // Você pode tentar redirecionar para a URL raiz:
            redirect("/"); 
        }
        
        Pessoa pessoa = Pessoa.findById(pessoaId);
        Atendimento atendimento = Atendimento.findById(atendimentoId); 
        
        // --- Tratamento para evitar NullPointerException (Entidade não encontrada) ---
        if (pessoa == null || atendimento == null) {
            flash.error("Pessoa ou Atendimento não encontrados. Verifique os dados.");
            
            // Aqui, pelo menos, temos o atendimentoId, então podemos voltar ao formulário.
            if (atendimentoId != null) {
                form(atendimentoId);
            } else {
                redirect("/"); // Opção de redirecionamento mais segura
            }
        }

        // --- Lógica de Negócio ---
        
        // Inicializa a lista de membros se for null (evitando NullPointerException)
        if (atendimento.membros == null) {
            atendimento.membros = new ArrayList<Pessoa>();
        }
        
        // Verifica se a pessoa já é membro
        if (atendimento.membros.contains(pessoa)) {
            flash.error("Essa pessoa já esta neste serviço!");
            form(atendimentoId);
        }
        
        // Adiciona e salva
        atendimento.membros.add(pessoa);
        atendimento.save();
        
        flash.success("Membro adicionado com sucesso!");
        form(atendimentoId);
    }
    
    // Método 'remover' ajustado para redirecionar para 'form' em caso de erro grave
    public static void remover(Long atendimentoId, Long pessoaId) {
        // Tratamento para IDs nulos
        if (pessoaId == null || atendimentoId == null) {
            flash.error("IDs de Pessoa e Atendimento são obrigatórios. Falha ao remover membro.");
            redirect("/"); // Redirecionamento seguro, pois atendimentoId pode ser nulo
        }

        Pessoa pessoa = Pessoa.findById(pessoaId);
        Atendimento atendimento = Atendimento.findById(atendimentoId);
        
        // Tratamento para entidades não encontradas
        if (pessoa == null || atendimento == null) {
            flash.error("Pessoa ou Atendimento não encontrados.");
            if (atendimentoId != null) {
                form(atendimentoId);
            } else {
                redirect("/");
            }
        }

        // Inicializa a lista de membros se for null antes de usar .contains()
        if (atendimento.membros == null) {
            atendimento.membros = new ArrayList<Pessoa>();
        }

        if (!atendimento.membros.contains(pessoa)) {
            flash.error("Essa pessoa não é membro da equipe!");
            form(atendimentoId);
        }
        
        atendimento.membros.remove(pessoa);
        atendimento.save();
        
        flash.success("Pessoa removida com sucesso!");
        form(atendimentoId);
    }
}