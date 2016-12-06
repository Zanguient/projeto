package br.gov.df.emater.aterwebsrv.bo_planejamento.planejamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Cadeia;
import br.gov.df.emater.aterwebsrv.bo.seguranca.AutenticarUsuarioCmd;
import br.gov.df.emater.aterwebsrv.bo.seguranca.LogCmd;

@Service("PlanejamentoNovoCh")
public class NovoCh extends _Cadeia {

	@Autowired
	public NovoCh(AutenticarUsuarioCmd c1, @Qualifier("PlanejamentoNovoCmd") NovoCmd c2, LogCmd c3) {
		super.addCommand(c1);
		super.addCommand(c2);
		super.addCommand(c3);
	}

	// @Autowired
	// public NovoCh(AutenticarUsuarioCmd c1, LogCmd c2) {
	// super.addCommand(c1);
	// super.addCommand(c2);
	// }

}