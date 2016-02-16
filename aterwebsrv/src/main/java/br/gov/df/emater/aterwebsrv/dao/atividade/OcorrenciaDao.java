package br.gov.df.emater.aterwebsrv.dao.atividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.df.emater.aterwebsrv.modelo.atividade.Ocorrencia;

@Repository("OcorrenciaDao")
public interface OcorrenciaDao extends JpaRepository<Ocorrencia, Integer> {

}