package br.gov.df.emater.importa;

import java.util.ArrayList;
import java.util.List;

public class RegimeCasamento {

	private List<String> sisater = new ArrayList<String>();
	private List<String> aterwebid = new ArrayList<String>();
	
	public RegimeCasamento(){
		this.sisater.add("null");					this.aterwebid.add(null);		
		this.sisater.add("Comunh�o parcial"); 		this.aterwebid.add("P");
		this.sisater.add("Comunh�o total"); 		this.aterwebid.add("U");
		this.sisater.add("Separa��o de bens"); 		this.aterwebid.add("S");

	/*
		P Comunh�o Parcial de Bens
		U Comunh�o Universal de Bens
		S Separa��o Total de Bens
		A Participa��o Final nos Aquestos	
	*/
	}

	public String getSisater( String val ){
		Integer pos = this.sisater.indexOf(val);
		return aterwebid.get(pos == -1 ? 0 : pos);		
	}	
}
