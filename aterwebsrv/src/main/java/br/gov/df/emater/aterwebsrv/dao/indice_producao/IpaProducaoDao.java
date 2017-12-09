package br.gov.df.emater.aterwebsrv.dao.indice_producao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.df.emater.aterwebsrv.modelo.indice_producao.IpaProducao;

@Repository("IpaProducaoDao")
public interface IpaProducaoDao extends JpaRepository<IpaProducao, Integer> {
	
	@Query("SELECT p FROM IpaProducao p, Ipa ipa WHERE  p.ipa = ipa.id  AND ipa.ano = :ano AND ipa.unidadeOrganizacional.id = :unidadeOrganizacionalId")
	List<IpaProducao> findByIpaProducao(@Param("ano") Integer ano, @Param("unidadeOrganizacionalId") Integer unidadeOrganizacionalId);
	
	@Query("SELECT p FROM IpaProducao p, Ipa ipa WHERE ipa.id = p.ipa  AND ipa.publicoAlvo.id = :publicoAlvo AND ipa.propriedadeRural.id = :propriedadeRuralId")
	List<IpaProducao> findByIpaProd(@Param("publicoAlvo") Integer ano, @Param("propriedadeRuralId") Integer propriedadeRuralId);

	@Modifying(clearAutomatically = true)
    @Transactional
	@Query("DELETE IpaProducao p WHERE p.ipa.id =:ipa")
	void deleteIpa(@Param("ipa") Integer ipa);

	@Query("SELECT p FROM IpaProducao p WHERE p.ipa.id =:ipa ")
	IpaProducao retornaId(@Param("ipa") Integer ipa);
	
	@Query("SELECT a FROM IpaProducao a WHERE a.id =:ipa ")
	List<IpaProducao> findByIp(@Param("ipa") Integer ipa);
	
	
	//@Query("SELECT distinct p FROM IpaProducao pp, IpaProducaoBemClassificado p, IpaProducaoForma f, Ipa ipa WHERE pp.id = f.ipaProducao AND pp.id = p.ipaProducao AND ipa.id = pp.ipa  AND ipa.ano = :ano AND ipa.unidadeOrganizacional.id = :unidadeOrganizacionalId")
	//List<IpaProducao> findByIpaProducao(@Param("ano") Integer ano, @Param("unidadeOrganizacionalId") Integer unidadeOrganizacionalId);

}
