package br.gov.df.emater.aterwebsrv.bo.pessoa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.bo.pendencia.VerificarPendenciasCmd;
import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioString;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PendenciaCodigo;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;

@Service("PessoaVerifPendTelefoneCmd")
public class VerifPendTelefoneCmd extends VerificarPendenciasCmd {

	@Override
	public String constatarPendencia(_Contexto contexto) {
		Pessoa pessoa = (Pessoa) getPendenciavel();

		List<String> mensagemList = new ArrayList<String>();
		if (pessoa.getTelefoneList() != null) {
			for (int i = pessoa.getTelefoneList().size() - 1; i >= 0; i--) {
				if (pessoa.getTelefoneList().get(i).getTelefone() == null) {
					pessoa.getTelefoneList().remove(i);
				}
				String informado = pessoa.getTelefoneList().get(i).getTelefone().getNumero();
				if (informado == null || informado.trim().length() == 0) {
					pessoa.getTelefoneList().remove(i);
				}
				if (!UtilitarioString.soNumero(informado).equals(UtilitarioString.soNumero(UtilitarioString.formataTelefone(informado)))) {
					mensagemList.add(String.format("Telefone, número ou formato inválido [%s]", informado));
				} else {
					pessoa.getTelefoneList().get(i).getTelefone().setNumero(UtilitarioString.formataTelefone(informado));
				}
			}
		}
		return extraiResultado(mensagemList);
	}

	@Override
	public PendenciaCodigo getCodigo() {
		return PendenciaCodigo.TELEFONE;
	}

}