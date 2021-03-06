package br.gov.df.emater.aterwebsrv.modelo.pessoa;

import static br.gov.df.emater.aterwebsrv.modelo.UtilitarioInfoBasica.infoBasicaReg;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo.InfoBasica;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.rest.json.JsonDeserializerData;
import br.gov.df.emater.aterwebsrv.rest.json.JsonSerializerData;

/**
 * The persistent class for the relacionamento database table.
 * 
 */

@Entity
@Table(name = "relacionamento", schema = EntidadeBase.PESSOA_SCHEMA)
@Inheritance(strategy = InheritanceType.JOINED)
// para identificar classes dentro de contextos polimorficos
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class Relacionamento extends EntidadeBase implements _ChavePrimaria<Integer>, InfoBasica<Relacionamento> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar inicio;

	@OneToMany(mappedBy = "relacionamento")
	private List<PessoaRelacionamento> pessoaRelacionamentoList;

	@ManyToOne
	@JoinColumn(name = "relacionamento_tipo_id")
	private RelacionamentoTipo relacionamentoTipo;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar termino;

	public Relacionamento() {
	}

	public Relacionamento(Integer id, Calendar inicio, Calendar termino, RelacionamentoTipo relacionamentoTipo) {
		setId(id);
		setInicio(inicio);
		setTermino(termino);
		setRelacionamentoTipo(relacionamentoTipo);
	}

	public Relacionamento(Serializable id) {
		super(id);
	}

	@Override
	public Integer getId() {
		return id;
	}

	public Calendar getInicio() {
		return inicio;
	}

	public List<PessoaRelacionamento> getPessoaRelacionamentoList() {
		return pessoaRelacionamentoList;
	}

	public RelacionamentoTipo getRelacionamentoTipo() {
		return relacionamentoTipo;
	}

	public Calendar getTermino() {
		return termino;
	}

	@Override
	public Relacionamento infoBasica() {
		return new Relacionamento(getId(), getInicio(), getTermino(), infoBasicaReg(getRelacionamentoTipo()));
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	public void setPessoaRelacionamentoList(List<PessoaRelacionamento> pessoaRelacionamentoList) {
		this.pessoaRelacionamentoList = pessoaRelacionamentoList;
	}

	public void setRelacionamentoTipo(RelacionamentoTipo relacionamentoTipo) {
		this.relacionamentoTipo = relacionamentoTipo;
	}

	public void setTermino(Calendar termino) {
		this.termino = termino;
	}

}