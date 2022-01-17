package com.java.automation.api.step.definitions;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import com.java.automation.api.ApiRegister;
import com.java.automation.api.model.ResponseClass;
import com.java.automation.utils.GlobalUtils;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class PersonSD extends ApiRegister {

	@Quando("eu realizar a chamada para a api person inserindo {string}, {string} e {string}")
	public void euRealizarAChamadaParaAApiPersonInserindoE(
		String nome,
		String idade,
		String comentarios
	)
	{
		getPersonApi().insertPerson(nome, idade, comentarios);
	}

	@Então("o status code deverá ser {int}")
	public void oStatusCodeDeveráSer(Integer httpStatusCode) {
		GlobalUtils.assertHttpStatusCode(httpStatusCode);
	}

	@Quando("eu realizar a chamada para api person atualizando {string}, {string}, {string} e {string}")
	public void euRealizarAChamadaParaApiPersonAtualizandoE(
		String id,
		String nome,
		String idade,
		String comentarios
	)
	{
		getPersonApi().updatePersonUsingId(id, nome, idade, comentarios);
	}

	@Quando("eu realizar uma chamada para a api persons")
	public void euRealizarUmaChamadaParaAApiPersons() {
		getPersonApi().getPersons();
	}

	@Então("a lista de pessoas cadastradas deverá ser retornada")
	public void aListaDePessoasCadastradasDeveráSerRetornada() {
		System.out.println(
			"<< Retorno da pesquisa: " + ResponseClass.getResponse() + " >>"
		);
	}

	@Quando("eu chamo a api person e passo o paramêtro {string} para a pesquisa")
	public void euChamoAApiPersonEPassoOParamêtroParaAPesquisa(String id) {
		getPersonApi().getPersonById(id);
	}

	@SuppressWarnings("deprecation")
	@Então("deverá retornar o {string}, a {string} e o {string}")
	public void deveráRetornarOAEO(String nome, String idade, String comentarios) {
		GlobalUtils.compareValueResponseString(nome);
		GlobalUtils.compareValueResponseString(idade);
		Assert.assertThat(
			ResponseClass.getResponse(),
			CoreMatchers.containsString(comentarios)
		);
	}

	@Quando("eu chamo a api person e passo o paramêtro {string} para exclusão")
	public void euChamoAApiPersonEPassoOParamêtroParaExclusão(String id) {
		// getPersonApi().deletePersonById(id);
	}
}