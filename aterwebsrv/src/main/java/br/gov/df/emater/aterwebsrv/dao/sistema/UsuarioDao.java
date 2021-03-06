package br.gov.df.emater.aterwebsrv.dao.sistema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaTipo;
import br.gov.df.emater.aterwebsrv.modelo.funcional.UnidadeOrganizacional;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;
import br.gov.df.emater.aterwebsrv.modelo.sistema.Usuario;

@Repository("UsuarioDao")
public interface UsuarioDao extends JpaRepository<Usuario, Integer>, UsuarioDaoCustom {

	List<Usuario> findByPessoa(Pessoa pessoa);

	Usuario findByPessoaEmailEmailEndereco(String email);

	List<Usuario> findByPessoaPessoaTipoAndUsuarioAtualizouPerfilOrderByPessoaNome(PessoaTipo pessoaTipo, Confirmacao s);

	List<Usuario> findByUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional);

	Usuario findByUsername(String nomeUsuario);

	Usuario findOneByPessoa(Pessoa pessoa);

}