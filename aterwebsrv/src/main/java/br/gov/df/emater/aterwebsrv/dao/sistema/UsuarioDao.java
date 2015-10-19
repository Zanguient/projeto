package br.gov.df.emater.aterwebsrv.dao.sistema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;
import br.gov.df.emater.aterwebsrv.modelo.sistema.Usuario;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String nomeUsuario);

	List<Usuario> findByPessoa(Pessoa pessoa);

}