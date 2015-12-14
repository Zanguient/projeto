package br.gov.df.emater.aterwebsrv.modelo.indice_producao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;

@Entity
@Table(name = "forma_producao_item", schema = EntidadeBase.INDICE_PRODUCAO_SCHEMA)
public class FormaProducaoItem extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "formaProducaoItem")
	private List<FormaProducaoValor> formaProducaoValorList;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;

	public FormaProducaoItem() {
		super();
	}

	public List<FormaProducaoValor> getFormaProducaoValorList() {
		return formaProducaoValorList;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setFormaProducaoValorList(List<FormaProducaoValor> formaProducaoValorList) {
		this.formaProducaoValorList = formaProducaoValorList;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}