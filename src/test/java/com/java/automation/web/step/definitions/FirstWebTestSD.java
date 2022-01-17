package com.java.automation.web.step.definitions;

import org.openqa.selenium.Keys;

import com.java.automation.web.pages.PageRegister;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class FirstWebTestSD extends PageRegister {

	@Dado("que eu acesso a {string}")
	public void queEuAcessoA(String url) {
		getFirstWebTestPG().accessUrl(url);
	}

	@Quando("eu preencho o campo de pesquisa com {string}")
	public void euPreenchoOCampoDePesquisaCom(String texto) {
		getFirstWebTestPG().getSearchTextBox().sendKeys(texto);
		getFirstWebTestPG().getSearchTextBox().sendKeys(Keys.ENTER);
	}

	@Quando("clico no primeiro link da pesquisa")
	public void clicoNoPrimeiroLinkDaPesquisa() {
		getFirstWebTestPG().getResultSearchLinks().get(0).click();
	}

	@Então("o {string} deverá ser apresentado")
	public void oDeveráSerApresentado(String titulo) {
		getFirstWebTestPG().validateTitle(titulo);
	}
}