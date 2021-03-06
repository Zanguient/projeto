package br.gov.df.emater.aterwebsrv.importador;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.df.emater.aterwebsrv.rest.Resposta;

@RestController
@RequestMapping("/importacao")
public class ImportadorRest {

	@Autowired
	private FacadeBoImportar facadeBo;

	public ImportadorRest() {
	}

	@RequestMapping(value = "/importar")
	public Resposta filtroExecutar(Principal usuario) throws Exception {
		System.out.println("pasosu");
		return new Resposta(facadeBo.importar(usuario).getResposta());
	}

}