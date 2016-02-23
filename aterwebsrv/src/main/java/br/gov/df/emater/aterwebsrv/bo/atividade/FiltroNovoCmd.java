package br.gov.df.emater.aterwebsrv.bo.atividade;

import java.util.Calendar;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.modelo.dto.AtividadeCadFiltroDto;

@Service("AtividadeFiltroNovoCmd")
public class FiltroNovoCmd implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		AtividadeCadFiltroDto filtro = new AtividadeCadFiltroDto();
		
		Calendar hoje = Calendar.getInstance();
		
		Calendar inicio = Calendar.getInstance();
		inicio.set(hoje.get(Calendar.YEAR), hoje.get(Calendar.YEAR), hoje.get(Calendar.DATE));
		inicio.set(Calendar.MONTH, -1);
		
		Calendar termino = Calendar.getInstance();
		termino.set(hoje.get(Calendar.YEAR), hoje.get(Calendar.YEAR), hoje.get(Calendar.DATE));
		
		filtro.setInicio(inicio);
		filtro.setTermino(termino);
		
		context.put("resultado", filtro);
		return false;
	}

}