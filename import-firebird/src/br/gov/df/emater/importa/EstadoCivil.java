package br.gov.df.emater.importa;

import java.util.ArrayList;
import java.util.List;

public class EstadoCivil {
	
	private List<String> sisater = new ArrayList<String>();
	private List<String> aterwebid = new ArrayList<String>();
	
	public EstadoCivil(){
		this.sisater.add("null");		this.aterwebid.add(null);		
		this.sisater.add("Amasiada");	this.aterwebid.add("U");
		this.sisater.add("Amasiado");	this.aterwebid.add("U");
		this.sisater.add("Casada");		this.aterwebid.add("C");
		this.sisater.add("Casado");		this.aterwebid.add("C");
		this.sisater.add("Desquitado");	this.aterwebid.add("P");
		this.sisater.add("Divorciado");	this.aterwebid.add("P");
		this.sisater.add("Falecida");	this.aterwebid.add(null);
		this.sisater.add("Juntado");	this.aterwebid.add("U");
		this.sisater.add("SOLTEIRO");	this.aterwebid.add("S");
		this.sisater.add("Separado");	this.aterwebid.add("P");
		this.sisater.add("Solteira");	this.aterwebid.add("S");
		this.sisater.add("Solteiro");	this.aterwebid.add("S");
		this.sisater.add("Uni�o Est�");	this.aterwebid.add("U");
		this.sisater.add("Uni�o est�");	this.aterwebid.add("U");
		this.sisater.add("Vi�va");		this.aterwebid.add("V");
		this.sisater.add("Vi�vo");		this.aterwebid.add("V");
		this.sisater.add("amasiado");	this.aterwebid.add("U");
		this.sisater.add("casada");		this.aterwebid.add("C");
		this.sisater.add("casado");		this.aterwebid.add("C");
		this.sisater.add("juntado");	this.aterwebid.add("C");
		this.sisater.add("solteiro");	this.aterwebid.add("S");
		this.sisater.add("uni�o est�");	this.aterwebid.add("U");
		this.sisater.add("vi�vo");		this.aterwebid.add("V");

/*
		S = Solteiro
		C = Casado
		U = Uni�o Est�vel
		P = Separado
		D = Desquitado
		V = Vi�vo
*/
	}
	
	public String getSisater( String val ){
		Integer pos = this.sisater.indexOf(val);
		return aterwebid.get(pos == -1 ? 0 : pos);		
	}	

}
