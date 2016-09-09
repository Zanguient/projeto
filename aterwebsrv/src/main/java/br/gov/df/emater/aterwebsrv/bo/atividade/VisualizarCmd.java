package br.gov.df.emater.aterwebsrv.bo.atividade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo.BoException;
import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.dao.atividade.AtividadeDao;
import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo.atividade.Atividade;
import br.gov.df.emater.aterwebsrv.modelo.atividade.AtividadeAssunto;
import br.gov.df.emater.aterwebsrv.modelo.atividade.AtividadePessoa;
import br.gov.df.emater.aterwebsrv.modelo.atividade.Ocorrencia;

@Service("AtividadeVisualizarCmd")
public class VisualizarCmd extends _Comando {

	@Autowired
	private AtividadeDao dao;

	@PersistenceContext(unitName = EntidadeBase.PERSISTENCE_UNIT)
	private EntityManager em;

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		Integer id = (Integer) contexto.getRequisicao();
		Atividade result = dao.findOne(id);

		if (result == null) {
			throw new BoException("Registro não localizado");
		}

		// limpar dados
		result.setAlteracaoUsuario(result.getAlteracaoUsuario().infoBasica());
		result.setInclusaoUsuario(result.getInclusaoUsuario().infoBasica());

		if (result.getAssuntoList() != null) {
			List<AtividadeAssunto> l = new ArrayList<AtividadeAssunto>();
			for (AtividadeAssunto r : result.getAssuntoList()) {
				AtividadeAssunto n = new AtividadeAssunto();
				n.setId(r.getId());
				n.setAssunto(r.getAssunto().infoBasica());
				n.setObservacao(r.getObservacao());
				l.add(n);
			}
			result.setAssuntoList(l);
		}

		if (result.getPessoaDemandanteList() != null) {
			List<AtividadePessoa> l = new ArrayList<AtividadePessoa>();
			for (AtividadePessoa r : result.getPessoaDemandanteList()) {
				AtividadePessoa n = new AtividadePessoa();
				n.setId(r.getId());
				n.setPessoa(r.getPessoa().infoBasica());
				n.setResponsavel(r.getResponsavel());
				n.setInicio(r.getInicio());
				n.setAtivo(r.getAtivo());
				n.setTermino(r.getTermino());
				n.setDuracao(r.getDuracao());
				l.add(n);
			}
			result.setPessoaDemandanteList(l);
		}

		if (result.getPessoaExecutorList() != null) {
			List<AtividadePessoa> l = new ArrayList<AtividadePessoa>();
			for (AtividadePessoa r : result.getPessoaExecutorList()) {
				AtividadePessoa n = new AtividadePessoa();
				n.setId(r.getId());
				n.setPessoa(r.getPessoa() == null ? null : r.getPessoa().infoBasica());
				n.setUnidadeOrganizacional(r.getUnidadeOrganizacional() == null ? null : r.getUnidadeOrganizacional().infoBasica());
				n.setResponsavel(r.getResponsavel());
				n.setInicio(r.getInicio());
				n.setAtivo(r.getAtivo());
				n.setTermino(r.getTermino());
				n.setDuracao(r.getDuracao());
				l.add(n);
			}
			result.setPessoaExecutorList(l);
		}

		if (result.getOcorrenciaList() != null) {
			List<Ocorrencia> l = new ArrayList<Ocorrencia>();
			for (Ocorrencia r : result.getOcorrenciaList()) {
				Ocorrencia n = new Ocorrencia();
				n.setId(r.getId());
				n.setUsuario(r.getUsuario().infoBasica());
				n.setRegistro(r.getRegistro());
				n.setRelato(r.getRelato());
				n.setIncidente(r.getIncidente());
				n.setAutomatico(r.getAutomatico());
				l.add(n);
			}
			result.setOcorrenciaList(l);
		}

		em.detach(result);
		contexto.setResposta(result);

		return false;
	}
}