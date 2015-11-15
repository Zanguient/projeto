package br.gov.df.emater.aterwebsrv.dao.ater;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.gov.df.emater.aterwebsrv.modelo.ater.BaciaHidrografica;
import br.gov.df.emater.aterwebsrv.modelo.ater.Comunidade;
import br.gov.df.emater.aterwebsrv.modelo.ater.PublicoAlvoPropriedadeRural;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.modelo.dto.FiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.dto.PropriedadeRuralCadFiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Endereco;

public class PropriedadeRuralDaoImpl implements PropriedadeRuralDaoCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> filtrar(PropriedadeRuralCadFiltroDto filtro) {
		// objetos de trabalho
		List<Object[]> result = null;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql, sqlTemp;

		// construção do sql
		sql = new StringBuilder();
		sql.append("select p.id").append("\n");
		sql.append("     , p.nome").append("\n");
		sql.append("     , p.endereco").append("\n");
		sql.append("     , p.comunidade").append("\n");
		sql.append("     , p.baciaHidrografica").append("\n");
		sql.append("     , p.areaTotal").append("\n");
		sql.append("     , vinculados").append("\n");
		sql.append("from PropriedadeRural p").append("\n");
		sql.append("left join p.publicoAlvoPropriedadeRuralList vinculados").append("\n");

		if (filtro.getUnidadeOrganizacional() != null) {
			sql.append("join p.comunidade com").append("\n");
			sql.append("join p.comunidade.unidadeOrganizacionalComunidadeList unid").append("\n");
		}

		sql.append("where (1 = 1)").append("\n");

		if (!StringUtils.isEmpty(filtro.getPessoaVinculada())) {
			sql.append("and (").append("\n");
			sqlTemp = new StringBuilder();
			for (String nome : filtro.getPessoaVinculada().split(FiltroDto.SEPARADOR_CAMPO)) {
				if (sqlTemp.length() > 0) {
					sqlTemp.append(" or ");
				}
				params.add(String.format("%%%s%%", nome.trim()));
				sqlTemp.append(" (vinculados.publicoAlvo.pessoa.nome like ?").append(params.size());
				params.add(String.format("%%%s%%", nome.trim()));
				sqlTemp.append(" or vinculados.publicoAlvo.pessoa.apelidoSigla like ?").append(params.size()).append(")").append("\n");
			}
			sql.append(sqlTemp);
			sql.append(" )").append("\n");
		}

		if (!StringUtils.isEmpty(filtro.getNomePropriedade())) {
			sql.append("and (").append("\n");
			sqlTemp = new StringBuilder();
			for (String nome : filtro.getNomePropriedade().split(FiltroDto.SEPARADOR_CAMPO)) {
				if (sqlTemp.length() > 0) {
					sqlTemp.append(" or ");
				}
				params.add(String.format("%%%s%%", nome.trim()));
				sqlTemp.append(" p.nome like ?").append(params.size()).append("\n");
			}
			sql.append(sqlTemp);
			sql.append(" )").append("\n");
		}

		if (filtro.getUnidadeOrganizacional() != null && filtro.getUnidadeOrganizacional().getId() != null) {
			params.add(filtro.getUnidadeOrganizacional());
			sql.append("and unid.unidadeOrganizacional = ?").append(params.size()).append("\n");
		}

		if (filtro.getComunidade() != null && filtro.getComunidade().getId() != null) {
			params.add(filtro.getComunidade());
			sql.append("and p.comunidade = ?").append(params.size()).append("\n");
		}

		if (filtro.getAreaUtil() != null) {
			if (filtro.getAreaUtil().getAte() == null) {
				params.add(filtro.getAreaUtil().getDe());
				sql.append("and p.areaTotal > ?").append(params.size()).append("\n");
			} else if (filtro.getAreaUtil().getDe() == null) {
				params.add(filtro.getAreaUtil().getAte());
				sql.append("and p.areaTotal <= ?").append(params.size()).append("\n");
			} else {
				params.add(filtro.getAreaUtil().getDe());
				sql.append("and p.areaTotal > ?").append(params.size()).append("\n");
				params.add(filtro.getAreaUtil().getAte());
				sql.append("and p.areaTotal <= ?").append(params.size()).append("\n");
			}
		}

		if (!CollectionUtils.isEmpty(filtro.getOutorga()) && (Confirmacao.values().length != (filtro.getOutorga().size()))) {
			params.add(filtro.getOutorga());
			sql.append("and p.outorga in ?").append(params.size()).append("\n");
		}

		if (filtro.getSistemaProducao() != null && filtro.getSistemaProducao().getId() != null) {
			params.add(filtro.getSistemaProducao());
			sql.append("and p.sistemaProducao = ?").append(params.size()).append("\n");
		}

		if (filtro.getSituacaoFundiaria() != null) {
			params.add(filtro.getSituacaoFundiaria());
			sql.append("and p.situacaoFundiaria = ?").append(params.size()).append("\n");
		}

		sql.append("order by p.nome").append("\n");

		// criar a query
		TypedQuery<Object[]> query = em.createQuery(sql.toString(), Object[].class);

		// inserir os parametros
		for (int i = 1; i <= params.size(); i++) {
			query.setParameter(i, params.get(i - 1));
		}

		// definir a pagina a ser consultada
		filtro.configuraPaginacao(query);

		// executar a consulta
		result = query.getResultList();

		// retornar
		return processaResultado(result);
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> processaResultado(List<Object[]> lista) {
		if (lista == null || lista.size() == 0) {
			return lista;
		}
		List<Object[]> result = new ArrayList<Object[]>();

		Integer id = null;
		Object[] reg = null;
		int c = 0;
		for (Object[] l : lista) {
			if (id != (Integer) l[0]) {
				if (reg != null) {
					result.add(reg);					
				}
				reg = new Object[l.length];
				c = 0;
				reg[c] = l[c];
				reg[++c] = l[c];
				reg[++c] = Endereco.FORMATA((Endereco) l[c]);
				reg[++c] = (Comunidade) l[c] == null ? null : ((Comunidade) l[c]).getNome();
				reg[++c] = (BaciaHidrografica) l[c] == null ? null : ((BaciaHidrografica) l[c]).getNome();				
				reg[++c] = l[c];
				reg[++c] = new ArrayList<String>();
				
				id = (Integer) reg[0]; 
			}
			PublicoAlvoPropriedadeRural papr = (PublicoAlvoPropriedadeRural) l[c];
			if (papr != null) {
				((List<String>) reg[c]).add(papr.getPublicoAlvo().getPessoa().getNome());
			}
		}
		if (reg != null) {
			result.add(reg);					
		}

		return result;
	}

}