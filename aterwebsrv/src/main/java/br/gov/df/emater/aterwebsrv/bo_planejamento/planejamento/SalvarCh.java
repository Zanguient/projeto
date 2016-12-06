package br.gov.df.emater.aterwebsrv.bo_planejamento.planejamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Cadeia;
import br.gov.df.emater.aterwebsrv.bo.seguranca.AutenticarUsuarioCmd;
import br.gov.df.emater.aterwebsrv.bo.seguranca.LogCmd;

@Service("PropriedadeRuralSalvarCh")
public class SalvarCh extends _Cadeia {

	@Autowired
	public SalvarCh(AutenticarUsuarioCmd c1, SalvarCmd c2, LogCmd c3) {
		super.addCommand(c1);
		super.addCommand(c2);
		super.addCommand(c2);
	}

}