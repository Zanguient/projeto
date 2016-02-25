package br.gov.df.emater.importa;

import java.util.ArrayList;
import java.util.List;

public class Profissao {
	
	private List<String> sisater = new ArrayList<String>();
	private List<String> aterwebid = new ArrayList<String>();
	
	public Profissao(){
		this.sisater.add("null");					this.aterwebid.add(null);		
		this.sisater.add("Administrador"); 			this.aterwebid.add("1");
		this.sisater.add("Advogado"); 				this.aterwebid.add("2");
		this.sisater.add("Agricultor"); 			this.aterwebid.add("3");
		this.sisater.add("Agropecuarista"); 		this.aterwebid.add("4");
		this.sisater.add("Agr�nomo"); 				this.aterwebid.add("5");
		this.sisater.add("Analista de sistemas"); 	this.aterwebid.add("6");
		this.sisater.add("Artes�o"); 				this.aterwebid.add("7");
		this.sisater.add("Banc�rio"); 				this.aterwebid.add("8");
		this.sisater.add("Bi�logo"); 				this.aterwebid.add("9");
		this.sisater.add("Contador"); 				this.aterwebid.add("10");
		this.sisater.add("Dentista"); 				this.aterwebid.add("11");
		this.sisater.add("Economista"); 			this.aterwebid.add("12");
		this.sisater.add("Enfermeiro"); 			this.aterwebid.add("13");
		this.sisater.add("Engenheiro"); 			this.aterwebid.add("14");
		this.sisater.add("Funcion�rio p�blico"); 	this.aterwebid.add("15");
		this.sisater.add("F�sico"); 				this.aterwebid.add("16");
		this.sisater.add("Ge�grafo"); 				this.aterwebid.add("17");
		this.sisater.add("Jornalista"); 			this.aterwebid.add("18");
		this.sisater.add("Matem�tico"); 			this.aterwebid.add("19");
		this.sisater.add("Militar"); 				this.aterwebid.add("20");
		this.sisater.add("M�dico"); 				this.aterwebid.add("21");
		this.sisater.add("Outra"); 					this.aterwebid.add("22");
		this.sisater.add("Pecuarista"); 			this.aterwebid.add("23");
		this.sisater.add("Pescador"); 				this.aterwebid.add("24");
		this.sisater.add("Professor");				this.aterwebid.add("25");
		this.sisater.add("Profissional liberal");	this.aterwebid.add("26");
		this.sisater.add("Psic�logo"); 				this.aterwebid.add("27");
		this.sisater.add("Qu�mico"); 				this.aterwebid.add("28");
		this.sisater.add("Soci�logo"); 				this.aterwebid.add("29");
		this.sisater.add("Trabalhador na �explora��o agropecu�ria"); this.aterwebid.add("30");
		this.sisater.add("T�cnico da ci�ncia da sa�de humana"); this.aterwebid.add("31");
		this.sisater.add("T�cnico da produ��o agropecu�ria"); this.aterwebid.add("32");
		this.sisater.add("T�cnicos de n�vel m�dio"); this.aterwebid.add("33");
		this.sisater.add("Vendedor"); 				this.aterwebid.add("34");
		this.sisater.add("Veterin�rio"); 			this.aterwebid.add("35");
		this.sisater.add("Zootecnista"); 			this.aterwebid.add("36");

	/*
		tabela pessoa.profissao
	*/
	}

	public String getSisater( String val ){
		Integer pos = this.sisater.indexOf(val);
		return aterwebid.get(pos == -1 ? 0 : pos);		
	}	
	
}	