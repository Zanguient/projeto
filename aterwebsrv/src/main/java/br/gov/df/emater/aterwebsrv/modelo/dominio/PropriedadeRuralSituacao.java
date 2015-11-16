package br.gov.df.emater.aterwebsrv.modelo.dominio;

public enum PropriedadeRuralSituacao {

	A("Ativo", 1), I("Inativo", 2);

	private String descricao;

	private Integer ordem;

	private PropriedadeRuralSituacao(String descricao, Integer ordem) {
		this.descricao = descricao;
		this.ordem = ordem;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}