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
			usuarioMaria.login = "teixeira";
			usuarioMaria.senha = "1111";
			usuarioMaria.save();
			
			Pessoa maria = new Pessoa();
			maria.nome = "Teixeirinha";
			maria.email = "teixeirinha@gmail.com";
			maria.tel = 892992999;
			maria.problema = SO;
			maria.usuario = usuarioMaria;
			maria.textoProblem = "Fala negão, fala dinho, como você ta? por aqui ta tudo bem. Saudade de voces tambem.";
			maria.save();
		
			Atendimento p1 = new Atendimento();
			p1.nome = "Problemas com Internet";
			p1.inicio = new Date();
			p1.fim = new Date();
			p1.save();
		}
	}

}