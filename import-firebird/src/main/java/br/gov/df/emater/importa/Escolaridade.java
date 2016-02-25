package br.gov.df.emater.importa;

import java.util.ArrayList;
import java.util.List;

public class Escolaridade {

	
	private List<String> sisater = new ArrayList<String>();
	private List<String> aterwebid = new ArrayList<String>();
	
	public Escolaridade(){
		this.sisater.add("null");					this.aterwebid.add(null);
		this.sisater.add("Analfabeto");				this.aterwebid.add("AI");
		this.sisater.add("Fundamental completo");	this.aterwebid.add("FC");
		this.sisater.add("Fundamental incompleto");	this.aterwebid.add("FI");
		this.sisater.add("M�dio completo");			this.aterwebid.add("MC");
		this.sisater.add("M�dio incompleto");		this.aterwebid.add("MI");
		this.sisater.add("Prim�rio completo");		this.aterwebid.add("FI");
		this.sisater.add("Prim�rio incompleto");	this.aterwebid.add("FI");
		this.sisater.add("Superior completo");		this.aterwebid.add("SC");
		this.sisater.add("Superior incompleto");	this.aterwebid.add("SI");
/*
		AI N�o sabe ler / escrever (Analfabeto)
		AC Sabe ler / escrever (Alfabetizado)
		FI Fundamental Incompleto
		FC Fundamental Completo
		MI M�dio Incompleto
		MC M�dio Completo
		SI Superior Incompleto
		SC Superior Completo
		ES Especializa��o/ Resid�ncia
		ME Mestrado
		DO Doutorado
*/
	}
	
	public String getSisater( String val ){
		Integer pos = this.sisater.indexOf(val);
		return aterwebid.get(pos == -1 ? 0 : pos);				   
	}	

	
}
