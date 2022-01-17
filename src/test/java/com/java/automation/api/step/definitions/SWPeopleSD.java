package com.java.automation.api.step.definitions;

import com.java.automation.api.ApiRegister;
import com.java.automation.utils.GlobalUtils;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class SWPeopleSD extends ApiRegister {

	@Dado("que eu acesso a api people {string}")
	public void queEuAcessoAApiPeople(String id) {
		getSWPeopleApi().getResponseBodyPeople(id);
	}

	@Quando("o http status code da api people for {int}")
	public void oHttpStatusCodeDaApiPeopleFor(Integer httpStatusCode) {
		getSWPeopleApi().checkHttpStatusCodePeople(httpStatusCode);
	}

	@Então("o parâmetro nome da personagem deverá ser {string}")
	public void oParâmetroNomeDaPersonagemDeveráSer(String nome) {
		GlobalUtils.getValueByIndexFromResponse("films");
		getSWPeopleApi().checkResponseBodyPeople(nome);
	}

	@Então("a altura deverá ser {string}")
	public void aAlturaDeveráSer(String altura) {
		getSWPeopleApi().checkResponseBodyPeople(altura);
	}

	@Então("o peso deverá ser {string}")
	public void oPesoDeveráSer(String peso) {
		getSWPeopleApi().checkResponseBodyPeople(peso);
	}

	@Então("a cor de cabelo deverá ser {string}")
	public void aCorDeCabeloDeveráSer(String corCabelo) {
		getSWPeopleApi().checkResponseBodyPeople(corCabelo);
	}

	@Então("a cor de pele deverá ser {string}")
	public void aCorDePeleDeveráSer(String corPele) {
		getSWPeopleApi().checkResponseBodyPeople(corPele);
	}

	@Então("a cor do olho deverá ser {string}")
	public void aCorDoOlhoDeveráSer(String corOlho) {
		getSWPeopleApi().checkResponseBodyPeople(corOlho);
	}

	@Então("a data de nascimento deverá ser {string}")
	public void aDataDeNascimentoDeveráSer(String dataNasc) {
		getSWPeopleApi().checkResponseBodyPeople(dataNasc);
	}

	@Então("o gênero deverá ser {string}")
	public void oGêneroDeveráSer(String genero) {
		getSWPeopleApi().checkResponseBodyPeople(genero);
	}

	@Então("o planeta natal deverá ser apresentado no serviço {string}")
	public void oPlanetaNatalDeveráSerApresentadoNoServiço(String planetaNatal) {
		getSWPeopleApi().checkResponseBodyPeople(planetaNatal);
	}

	@Então("os filmes deverão ser apresentados nos serviços")
	public void osFilmesDeverãoSerApresentadosNosServiços(DataTable urlFilmes) {
		System.out.print("\n");
		getSWPeopleApi().checkResponseBodyPeople(
			urlFilmes,
			GlobalUtils.getFirstIndexDataTable(urlFilmes)
		);
	}

	@Então("as espécies deverão ser apresentados no serviço")
	public void asEspéciesDeverãoSerApresentadosNoServiço(DataTable urlEspecies) {
		System.out.print("\n");
		// getSWPeopleApi().checkResponseBodyPeople(urlEspecies);
	}

	@Então("os veículos deverão ser apresentados no serviço")
	public void osVeículosDeverãoSerApresentadosNoServiço(DataTable urlVeiculos) {
		System.out.print("\n");
		getSWPeopleApi().checkResponseBodyPeople(urlVeiculos);
	}

	@Então("as naves estelares deverão ser apresentados no serviço")
	public void asNavesEstelaresDeverãoSerApresentadosNoServiço(DataTable urlNavesEstrelares) {
		getSWPeopleApi().checkResponseBodyPeople(urlNavesEstrelares);
	}

	@Então("a data de criação deverá ser {string}")
	public void aDataDeCriaçãoDeveráSer(String data_criacao) {
		getSWPeopleApi().checkResponseBodyPeople(data_criacao);
	}

	@Então("a data de edição deverá ser {string}")
	public void aDataDeEdiçãoDeveráSer(String data_edicao) {
		getSWPeopleApi().checkResponseBodyPeople(data_edicao);
	}

	@Então("o endereço de acesso contendo as informação deverá ser {string}")
	public void oEndereçoDeAcessoContendoAsInformaçãoDeveráSer(String url) {
		getSWPeopleApi().checkResponseBodyPeople(url);
	}
}