package jobs;

import java.util.Date;

import models.Problema;
import models.Responsavel;
import models.Usuario;
import models.Atendimento;
import models.Perfil;
import models.Pessoa;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {
	
	@Override
	public void doJob() throws Exception {
		if (Problema.count() == 0) {
			Problema internet = new Problema("Internet", 100);
			internet.save();
			
			Problema hardware = new Problema("Hardware", 101);
			hardware.save();
			
			Problema software = new Problema("Software", 102);
			software.save();
			
			Problema SO = new Problema("Sistema Operacional", 103);
			SO.save();
			
			
			Usuario usuarioJoao = new Usuario();
			usuarioJoao.login = "joaogameskk";
			usuarioJoao.senha = "123321"; 
			usuarioJoao.perfil = Perfil.RESPONSAVEL;
			usuarioJoao.save();
			
			Responsavel joao = new Responsavel();
			joao.usuario = usuarioJoao;
			joao.save();
		
			
			Usuario usuarioMaria = new Usuario();
			usuarioMaria.login = "mariaroberta";
			usuarioMaria.senha = "123321";
			usuarioMaria.perfil = Perfil.PESSOA;
			usuarioMaria.save();
			
			Pessoa maria = new Pessoa();
			maria.nome = "Maria Roberta";
			maria.email = "robertinha@gmail.com";
			maria.tel = 892992999;
			maria.problema = SO;
			maria.usuario = usuarioMaria;
			maria.textoProblem = "O notebook não passa da tela de login";
			maria.save();
			
			Atendimento at1 = new Atendimento();
			at1.nome = "Problemas com Internet";
			at1.inicio = new Date();
			at1.fim = new Date();
			at1.save();
		}
	}

}