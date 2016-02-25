package br.gov.df.emater.importa;

import java.util.ArrayList;
import java.util.List;

public class Cidade {
	
	private List<String> nome = new ArrayList<String>();
	private List<String> codigo = new ArrayList<String>();
	
	public Cidade(){
		this.nome.add("Bras�lia");this.codigo.add("1");
		this.nome.add("�gua Fria");this.codigo.add("2");
		this.nome.add("Cocalzinho");this.codigo.add("3");
		this.nome.add("Formosa");this.codigo.add("4");
		this.nome.add("Planaltina de Goi�s");this.codigo.add("6");
		this.nome.add("Planltina de Goi�s");this.codigo.add("6");
		this.nome.add("Cristalina");this.codigo.add("5");
		this.nome.add("S�o Marcos");this.codigo.add("5");
		this.nome.add("�guas Claras");this.codigo.add("8");	
		this.nome.add("Alexandre Gusm�o");this.codigo.add("9");
		this.nome.add("Brazl�ndia");this.codigo.add("10");
		this.nome.add("Ceil�ndia");this.codigo.add("11");
		this.nome.add("Gama");this.codigo.add("12");
		this.nome.add("Guar�");this.codigo.add("13");
		this.nome.add("Lago Norte");this.codigo.add("14");
		this.nome.add("N�cleo bandeirante");this.codigo.add("15");
		this.nome.add("Padre Bernardo");this.codigo.add("16");
		this.nome.add("Parano�");this.codigo.add("17");
		this.nome.add("Park Way");this.codigo.add("18");
		this.nome.add("Planaltina");this.codigo.add("19");
		this.nome.add("Recanto das Emas");this.codigo.add("20");
		this.nome.add("Riacho Fundo I");this.codigo.add("21");
		this.nome.add("Riacho Fundo II");this.codigo.add("22");
		this.nome.add("Samambaia");this.codigo.add("23");
		this.nome.add("Santa Maria");this.codigo.add("24");
		this.nome.add("S�o Marcos");this.codigo.add("25");
		this.nome.add("S�o Sebasti�o");this.codigo.add("26");
		this.nome.add("SCIA");this.codigo.add("27");
		this.nome.add("Sobradinho");this.codigo.add("28");
		this.nome.add("Taguatinga");this.codigo.add("29");
																																												
	}

	public String getCodigo(String val) {
		Integer pos = this.nome.indexOf(val);
		pos = pos == -1 ? 0 : pos;
		return codigo.get(pos);				   
	}

}
