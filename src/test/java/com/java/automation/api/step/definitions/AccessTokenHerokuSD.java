package com.java.automation.api.step.definitions;

import com.java.automation.api.ApiRegister;
import com.java.automation.api.model.HeaderClass;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;

public class AccessTokenHerokuSD extends ApiRegister {

	@Dado("que eu acesso a api para geração do token")
	public void queEuAcessoAApiParaGeraçãoDoToken() {
		getAccessTokenHerokuApi().getAccessToken();
	}

	@Dado("que eu acesso a api para geração do token usando o {string} e {string}")
	public void queEuAcessoAApiParaGeraçãoDoTokenUsandoOE(String usuario, String senha) {
		getAccessTokenHerokuApi().getAccessToken(usuario, senha);
	}

	@Então("um token de acesso deverá ser gerado")
	public void umTokenDeAcessoDeveráSerGerado() {
		System.out.println("<< Token generated: " + HeaderClass.getToken() + " >>");
	}
}