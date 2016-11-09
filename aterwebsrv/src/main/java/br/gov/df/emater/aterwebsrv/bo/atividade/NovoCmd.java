package br.gov.df.emater.aterwebsrv.bo.atividade;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.modelo.atividade.Atividade;
import br.gov.df.emater.aterwebsrv.modelo.dominio.AtividadeFinalidade;
import br.gov.df.emater.aterwebsrv.modelo.dominio.AtividadeFormato;
import br.gov.df.emater.aterwebsrv.modelo.dominio.AtividadeNatureza;
import br.gov.df.emater.aterwebsrv.modelo.dominio.AtividadePrioridade;
import br.gov.df.emater.aterwebsrv.modelo.dominio.AtividadeSituacao;

@Service("AtividadeNovoCmd")
public class NovoCmd extends _Comando {

//	@Autowired
//	private MetodoDao metodoDao;

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		Atividade result = (Atividade) contexto.getRequisicao();

		if (result == null) {
			result = new Atividade();
		}

		result.setFormato(AtividadeFormato.E);
		result.setPrioridade(AtividadePrioridade.N);
		result.setFinalidade(AtividadeFinalidade.O);
		result.setNatureza(AtividadeNatureza.D);
		result.setSituacao(AtividadeSituacao.C);

		// FIXME quebra galho para facilitar a construção do credito rural,
		// remover assim que terminar
		/*
		result.setPublicoEstimado(1);
		result.setMetodo(metodoDao.findOneByCodigo(MetodoCodigo.PROJETO_CREDITO_RURAL));
		List<AtividadeAssunto> assuntoList = new ArrayList<>();
		AtividadeAssunto atividadeAssunto = new AtividadeAssunto();
		atividadeAssunto.setAssunto(new Assunto(14));
		assuntoList.add(atividadeAssunto);
		result.setAssuntoList(assuntoList);

		List<AtividadePessoa> pessoaList = new ArrayList<>();
		AtividadePessoa atividadePessoa = new AtividadePessoa();
		atividadePessoa.setPessoa(new PessoaFisica(5909, "Agnaldo Joaquim dos Santos", null, null, PessoaSituacao.A, Confirmacao.S, PessoaGenero.M, null));
		atividadePessoa.setParticipacao(AtividadePessoaParticipacao.D);
		atividadePessoa.setAtivo(Confirmacao.S);
		atividadePessoa.setInicio(Calendar.getInstance());
		atividadePessoa.setResponsavel(Confirmacao.S);
		pessoaList.add(atividadePessoa);
		result.setPessoaDemandanteList(pessoaList);

		pessoaList = new ArrayList<>();
		atividadePessoa = new AtividadePessoa();
		atividadePessoa.setPessoa(new PessoaFisica(113, "Fernando Frazão da Silva", null, null, PessoaSituacao.A, Confirmacao.N, PessoaGenero.M, null));
		atividadePessoa.setParticipacao(AtividadePessoaParticipacao.E);
		atividadePessoa.setAtivo(Confirmacao.S);
		atividadePessoa.setInicio(Calendar.getInstance());
		atividadePessoa.setResponsavel(Confirmacao.S);
		pessoaList.add(atividadePessoa);
		result.setPessoaExecutorList(pessoaList);

		// cadastrar o credito
		ProjetoCreditoRural projetoCreditoRural = new ProjetoCreditoRural();

		List<PublicoAlvoPropriedadeRural> publicoAlvoPropriedadeRuralList = new ArrayList<>();

		PropriedadeRural propriedadeRural = new PropriedadeRural(2791, "Fazenda Desterro - Área isolada n°09", null, new BigDecimal("51"), null);

		PublicoAlvoPropriedadeRural publicoAlvoPropriedadeRural = new PublicoAlvoPropriedadeRural(2951);
		publicoAlvoPropriedadeRural.setPropriedadeRural(propriedadeRural);
		publicoAlvoPropriedadeRural.setVinculo(PropriedadeRuralVinculoTipo.PR);
		publicoAlvoPropriedadeRural.setArea(new BigDecimal("51"));
		publicoAlvoPropriedadeRuralList.add(publicoAlvoPropriedadeRural);

		propriedadeRural = new PropriedadeRural(2792, "Fazenda Desterro Aguimar", null, new BigDecimal("100.8"), null);
		publicoAlvoPropriedadeRural = new PublicoAlvoPropriedadeRural(3257);
		publicoAlvoPropriedadeRural.setPropriedadeRural(propriedadeRural);
		publicoAlvoPropriedadeRural.setVinculo(PropriedadeRuralVinculoTipo.PR);
		publicoAlvoPropriedadeRural.setArea(new BigDecimal("100.8"));
		publicoAlvoPropriedadeRuralList.add(publicoAlvoPropriedadeRural);

		PublicoAlvo publicoAlvo = new PublicoAlvo();
		publicoAlvo.setPessoa(new PessoaFisica(5452, "Agnaldo Joaquim dos Santos", null, null, PessoaSituacao.A, Confirmacao.S, PessoaGenero.M, null));
		publicoAlvo.setPublicoAlvoPropriedadeRuralList(publicoAlvoPropriedadeRuralList);

		projetoCreditoRural.setPublicoAlvo(publicoAlvo);

		projetoCreditoRural.setStatus(ProjetoCreditoRuralStatus.EA);
		AgenteFinanceiro agenteFinanceiro = new AgenteFinanceiro(1);
		List<AgenteFinanceiroLinhaCredito> linhaCreditoList = new ArrayList<>();
		linhaCreditoList.add(new AgenteFinanceiroLinhaCredito(1, new LinhaCredito(1, "CREDITRABALHO")));
		agenteFinanceiro.setLinhaCreditoList(linhaCreditoList);
		projetoCreditoRural.setAgenteFinanceiro(agenteFinanceiro);
		projetoCreditoRural.setAgencia("123");
		projetoCreditoRural.setLinhaCredito(new LinhaCredito(1));
		projetoCreditoRural.setNumeroCedula("123");

		result.setProjetoCreditoRural(projetoCreditoRural);
		// FIXME quebra galho para facilitar a construção do credito rural,
		// remover assim que terminar
		// */

		Calendar hoje = Calendar.getInstance();
		result.setInicio(hoje);

		Calendar amanha = Calendar.getInstance();
		amanha.setTimeInMillis(hoje.getTimeInMillis());
		// amanha.add(Calendar.DAY_OF_MONTH, 1);
		result.setPrevisaoConclusao(amanha);

		Calendar depoisDeAmanha = Calendar.getInstance();
		depoisDeAmanha.setTimeInMillis(hoje.getTimeInMillis());
		// depoisDeAmanha.add(Calendar.DAY_OF_MONTH, 2);
		result.setConclusao(depoisDeAmanha);

		result.setDuracaoEstimada(0);
		result.setDuracaoReal(0);
		result.setDuracaoSuspensao(0);

		contexto.setResposta(result);

		return true;
	}

}