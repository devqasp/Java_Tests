package com.java.automation.ui.step.definitions;

import com.java.automation.ui.pages.PageRegister;

import org.openqa.selenium.Keys;

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
		getFirstWebTestPG().getSearchTextBoxInList().get(0).sendKeys(texto);
		getFirstWebTestPG().getSearchTextBoxInList().get(0).sendKeys(Keys.ENTER);
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