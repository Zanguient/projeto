package br.gov.df.emater.aterwebsrv.bo.atividade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.dao.atividade.AtividadeDao;
import br.gov.df.emater.aterwebsrv.modelo.dto.AtividadeCadFiltroDto;

@Service("AtividadeFiltrarCmd")
public class FiltrarCmd extends _Comando {
	
	@Autowired
	private AtividadeDao atividadeDao;

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		System.out.println("Filtrando atividade...");
		AtividadeCadFiltroDto filtro = (AtividadeCadFiltroDto) contexto.getRequisicao();
		List<Object[]> result = null;
		result = atividadeDao.filtrar(filtro);
		contexto.setResposta(result);
		return false;
	}

}