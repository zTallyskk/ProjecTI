package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;

@Entity
public class Problema extends Model{
	
	public String tipo;
	public Integer codPro;
	
	public Problema() {
		
	}
		
	public Problema(String tipo, Integer codPro) {
		this.tipo = tipo;
		this.codPro = codPro;	
	}
	@Override
	public String toString () {
		return tipo + "(" + codPro +")";
	}

}
