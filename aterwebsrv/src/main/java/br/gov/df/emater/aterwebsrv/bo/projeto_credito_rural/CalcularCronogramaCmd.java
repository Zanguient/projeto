package br.gov.df.emater.aterwebsrv.bo.projeto_credito_rural;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo.BoException;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.bo._SalvarCmd;
import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioFinanceiro;
import br.gov.df.emater.aterwebsrv.modelo.dominio.FinanciamentoTipo;
import br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural.CronogramaPagamento;
import br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural.ProjetoCreditoRural;
import br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural.ProjetoCreditoRuralCronogramaPagamento;
import br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural.ProjetoCreditoRuralFinanciamento;

@Service("ProjetoCreditoRuralCalcularCronogramaCmd")
public class CalcularCronogramaCmd extends _SalvarCmd {

	public CalcularCronogramaCmd() {
	}

	private ProjetoCreditoRuralCronogramaPagamento getCronograma(ProjetoCreditoRural projetoCreditoRural) throws BoException {
		ProjetoCreditoRuralCronogramaPagamento result = null;

		result = procuraCronogramaPagamento(projetoCreditoRural.getCronogramaPagamentoInvestimentoList(), FinanciamentoTipo.I, projetoCreditoRural.getInvestimentoList());
		if (result != null) {
			return result;
		}

		result = procuraCronogramaPagamento(projetoCreditoRural.getCronogramaPagamentoCusteioList(), FinanciamentoTipo.C, projetoCreditoRural.getCusteioList());
		if (result != null) {
			return result;
		}

		throw new BoException("Tipo de cronograma de projeto de crédito rural não identificado!");
	}

	private ProjetoCreditoRuralCronogramaPagamento procuraCronogramaPagamento(List<ProjetoCreditoRuralCronogramaPagamento> cronogramaPagamentoList, FinanciamentoTipo financiamentoTipo, List<ProjetoCreditoRuralFinanciamento> financiamentoList) throws BoException {

		if (cronogramaPagamentoList != null) {
			for (ProjetoCreditoRuralCronogramaPagamento cP : cronogramaPagamentoList) {
				if (cP.getDataCalculo() == null) {
					BigDecimal valorFinanciado = new BigDecimal("0");
					if (financiamentoList != null) {
						for (ProjetoCreditoRuralFinanciamento f : financiamentoList) {
							if (cP.getNomeLote().equals(f.getNomeLote())) {
								valorFinanciado = valorFinanciado.add(f.getValorFinanciado());
							}
						}
					}
					cP.setTipo(financiamentoTipo);
					cP.setValorFinanciamento(valorFinanciado);
					return cP;
				}
			}
		}
		return null;
	}

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		ProjetoCreditoRural result = (ProjetoCreditoRural) contexto.getRequisicao();

		ProjetoCreditoRuralCronogramaPagamento cronograma = getCronograma(result);

		// criticar parâmetros
		if (cronograma.getPeriodicidade() == null) {
			throw new BoException("O campo [%s] não foi informado", "Periodicidade");
		}
		if (cronograma.getDataContratacao() == null) {
			throw new BoException("O campo [%s] não foi informado", "Data da Contratação");
		}
		if (cronograma.getValorFinanciamento() == null) {
			throw new BoException("O campo [%s] não foi informado", "Valor do Financiamento");
		}
		if (cronograma.getTaxaJurosAnual() == null) {
			throw new BoException("O campo [%s] não foi informado", "Juros Anual (%)");
		}
		if (cronograma.getQuantidadeParcelas() == null) {
			throw new BoException("O campo [%s] não foi informado", "Quantidade de Parcelas");
		}
		if (cronograma.getDataFinalCarencia() == null) {
			throw new BoException("O campo [%s] não foi informado", "Data Final de Carência");
		}
		if (cronograma.getQuantidadeParcelas() == null || cronograma.getQuantidadeParcelas() < 1) {
			throw new BoException("Quantidade mínima de parcelas ultrapassada [1]");
		}
		if (cronograma.getPeriodicidade().ultrapassaQuantidadeMaximaParcelas(cronograma.getQuantidadeParcelas())) {
			throw new BoException("Quantidade máxima de parcelas ultrapassada [%d]", cronograma.getPeriodicidade().getMaxParcelas());
		}
		if (cronograma.getDataFinalCarencia().before(cronograma.getDataContratacao())) {
			throw new BoException("A data final da carência deve ser superior ou igual a data de contratação");
		}

		// variáveis produzidas para a resposta do calculo
		List<CronogramaPagamento> cronogramaPagamentoList = new ArrayList<>();
		CronogramaPagamento cronogramaPagamento;
		Integer id = 0;

		// calcular a data da primeira parcela
		Calendar dataPrimeiraParcela = new GregorianCalendar();
		dataPrimeiraParcela.setTime(cronograma.getDataFinalCarencia().getTime());
		dataPrimeiraParcela.add(Calendar.DATE, cronograma.getPeriodicidade().getTotalDiasPeriodo());
		cronograma.setDataPrimeiraParcela(dataPrimeiraParcela);
		cronograma.setValorTotalJuros(new BigDecimal("0"));
		cronograma.setValorTotalPrestacoes(new BigDecimal("0"));

		// ajustar a taxa de juros conforme a periodicidade dos juros anuais
		// escolhida
		BigDecimal taxaJurosAnualAjustada = cronograma.getTaxaJurosAnual();
		switch (cronograma.getPeriodicidade()) {
		case A:
			taxaJurosAnualAjustada = UtilitarioFinanceiro.getInstance().converteTempoTaxa(taxaJurosAnualAjustada, UtilitarioFinanceiro.Periodicidade.AO_ANO.getFatorEmAnos());
			break;
		case M:
			taxaJurosAnualAjustada = UtilitarioFinanceiro.getInstance().converteTempoTaxa(taxaJurosAnualAjustada, UtilitarioFinanceiro.Periodicidade.AO_ANO.getFatorEmMeses());
			break;
		case S:
			taxaJurosAnualAjustada = UtilitarioFinanceiro.getInstance().converteTempoTaxa(taxaJurosAnualAjustada, UtilitarioFinanceiro.Periodicidade.AO_ANO.getFatorEmSemestres());
			break;
		case T:
			taxaJurosAnualAjustada = UtilitarioFinanceiro.getInstance().converteTempoTaxa(taxaJurosAnualAjustada, UtilitarioFinanceiro.Periodicidade.AO_ANO.getFatorEmTrimestres());
			break;
		}

		// inserir os juros da carência
		BigDecimal jurosCarencia = new BigDecimal("0");
		Long diasCarencia = TimeUnit.DAYS.convert(cronograma.getDataFinalCarencia().getTimeInMillis() - cronograma.getDataContratacao().getTimeInMillis(), TimeUnit.MILLISECONDS);
		if (diasCarencia.compareTo(new Long("1")) >= 0) {
			// transformar a taxa de juros para "ao dia"
			BigDecimal taxaJurosCarenciaEmDias = UtilitarioFinanceiro.getInstance().converteTempoTaxa(cronograma.getTaxaJurosAnual().divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP), UtilitarioFinanceiro.Periodicidade.AO_ANO.getFatorEmDias());
			// aplicar os juros diários fonte:
			// http://www.somatematica.com.br/emedio/finan3.php
			jurosCarencia = (cronograma.getValorFinanciamento().multiply((new BigDecimal("1").add(taxaJurosCarenciaEmDias)).pow(diasCarencia.intValue()))).subtract(cronograma.getValorFinanciamento());

			cronogramaPagamento = new CronogramaPagamento();
			cronogramaPagamento.setId(--id);
			// cronogramaPagamento.setTipo(cronograma.getTipo());
			cronogramaPagamento.setParcela(0);
			cronogramaPagamento.setSaldoDevedorInicial(cronograma.getValorFinanciamento());
			cronogramaPagamento.setPrestacao(new BigDecimal("0"));
			cronogramaPagamento.setTaxaJuros(taxaJurosCarenciaEmDias);
			cronogramaPagamento.setJuros(jurosCarencia);
			cronogramaPagamento.setAmortizacao(new BigDecimal("0"));
			cronogramaPagamento.setSaldoDevedorFinal(cronogramaPagamento.getSaldoDevedorInicial().add(jurosCarencia));

			cronograma.setValorTotalJuros(cronograma.getValorTotalJuros().add(cronogramaPagamento.getJuros()));

			cronogramaPagamentoList.add(cronogramaPagamento);
		}

		// calcular as demais prestações
		List<Map<String, Object>> tabelaPriceList = UtilitarioFinanceiro.getInstance().tabelaPriceCalculaParcelas(cronograma.getValorFinanciamento().add(jurosCarencia), taxaJurosAnualAjustada, cronograma.getQuantidadeParcelas());
		int ano = 1, epoca = 0;
		for (int i = 0; i < tabelaPriceList.size(); i++) {
			Map<String, Object> tabelaPrice = tabelaPriceList.get(i);

			cronogramaPagamento = new CronogramaPagamento();
			cronogramaPagamento.setId(--id);
			if (i == 0) {
				cronogramaPagamento.setAno(ano);
			}
			if ((++epoca) <= cronograma.getPeriodicidade().getMaxEpoca()) {
				cronogramaPagamento.setEpoca(epoca);
			} else {
				epoca = 1;
				cronogramaPagamento.setAno(++ano);
				cronogramaPagamento.setEpoca(epoca);
			}
			// cronogramaPagamento.setTipo(cronograma.getTipo());
			cronogramaPagamento.setParcela((Integer) tabelaPrice.get("parcela"));
			cronogramaPagamento.setSaldoDevedorInicial((BigDecimal) tabelaPrice.get("saldoDevedorInicial"));
			cronogramaPagamento.setPrestacao((BigDecimal) tabelaPrice.get("prestacao"));
			cronogramaPagamento.setTaxaJuros((BigDecimal) tabelaPrice.get("taxaJuros"));
			cronogramaPagamento.setJuros((BigDecimal) tabelaPrice.get("juros"));
			cronogramaPagamento.setAmortizacao((BigDecimal) tabelaPrice.get("amortizacao"));
			cronogramaPagamento.setSaldoDevedorFinal((BigDecimal) tabelaPrice.get("saldoDevedorFinal"));

			cronograma.setValorTotalJuros(cronograma.getValorTotalJuros().add(cronogramaPagamento.getJuros()));
			cronograma.setValorTotalPrestacoes(cronograma.getValorTotalPrestacoes().add(cronogramaPagamento.getPrestacao()));

			cronogramaPagamentoList.add(cronogramaPagamento);
		}

		cronograma.setCronogramaPagamentoList(cronogramaPagamentoList);
		cronograma.setDataCalculo(Calendar.getInstance());

		contexto.setResposta(result);

		return false;
	}

}