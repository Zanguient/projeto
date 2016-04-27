package br.gov.df.emater.aterwebsrv.bo.usuario;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.dao.sistema.UsuarioDao;
import br.gov.df.emater.aterwebsrv.modelo.sistema.Usuario;

@Service("UsuarioSalvarCmd")
public class SalvarCmd extends _Comando {

	@Autowired
	private UsuarioDao dao;

	public SalvarCmd() {
	}

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		Usuario result = (Usuario) contexto.getRequisicao();
		if (result.getId() == null) {
			result.setUsuarioInclusao(getUsuario(contexto.getUsuario().getName()));
			result.setInclusaoData(Calendar.getInstance());
		} else {
			result.setUsuarioInclusao(getUsuario(result.getUsuarioInclusao().getUsername()));
		}
		result.setUsuarioAlteracao(getUsuario(contexto.getUsuario().getName()));
		result.setAlteracaoData(Calendar.getInstance());

		Usuario salvo = null;
		if (result.getId() != null) {
			salvo = dao.findOne(result.getId());
		}

		// remover os itens descartados
		// if (salvo != null) {
		// if (salvo.getModuloFuncionalidadeList() != null) {
		// for (ModuloFuncionalidade moduloFuncionalidadeSalvo :
		// salvo.getModuloFuncionalidadeList()) {
		// boolean encontrou = false;
		// if (result.getModuloFuncionalidadeList() != null) {
		// for (ModuloFuncionalidade moduloFuncionalidade :
		// result.getModuloFuncionalidadeList()) {
		// if
		// (moduloFuncionalidade.getModulo().getId().equals(moduloFuncionalidadeSalvo.getModulo().getId()))
		// {
		// moduloFuncionalidade.setId(moduloFuncionalidadeSalvo.getId());
		// encontrou = true;
		// break;
		// }
		// }
		// }
		// if (!encontrou) {
		// moduloFuncionalidadeDao.delete(moduloFuncionalidadeSalvo);
		// }
		// }
		// }
		// if (salvo.getFuncionalidadeComandoList() != null) {
		// for (FuncionalidadeComando funcionalidadeComandoSalvo :
		// salvo.getFuncionalidadeComandoList()) {
		// boolean encontrou = false;
		// if (result.getFuncionalidadeComandoList() != null) {
		// for (FuncionalidadeComando funcionalidadeComando :
		// result.getFuncionalidadeComandoList()) {
		// if
		// (funcionalidadeComando.getComando().getId().equals(funcionalidadeComandoSalvo.getComando().getId()))
		// {
		// funcionalidadeComando.setId(funcionalidadeComandoSalvo.getId());
		// encontrou = true;
		// break;
		// }
		// }
		// }
		// if (!encontrou) {
		// funcionalidadeComandoDao.delete(funcionalidadeComandoSalvo);
		// }
		// }
		// }
		// }

		// preparar para salvar
		dao.save(result);

		// if (result.getModuloFuncionalidadeList() != null) {
		// for (ModuloFuncionalidade moduloFuncionalidade :
		// result.getModuloFuncionalidadeList()) {
		// moduloFuncionalidade.setFuncionalidade(result);
		// moduloFuncionalidadeDao.save(moduloFuncionalidade);
		// }
		// }
		// if (result.getFuncionalidadeComandoList() != null) {
		// for (FuncionalidadeComando funcionalidadeComando :
		// result.getFuncionalidadeComandoList()) {
		// funcionalidadeComando.setFuncionalidade(result);
		// funcionalidadeComandoDao.save(funcionalidadeComando);
		// }
		// }

		contexto.setResposta(result.getId());
		return false;
	}
}