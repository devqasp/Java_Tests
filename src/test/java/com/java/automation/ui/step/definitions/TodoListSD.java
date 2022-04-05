package com.java.automation.ui.step.definitions;

import static io.qameta.allure.Allure.step;

import com.java.automation.ui.pages.PageRegister;
import com.java.automation.utils.GlobalUtils;
import com.java.automation.utils.WebDriverUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class TodoListSD extends PageRegister {

	private WebDriver driver;
	private Wait<WebDriver> wait;

	public TodoListSD() {
		//
		this.driver = WebDriverUtils.getDriverManager().getDriver();
		//
		this.wait   = WebDriverUtils.getWait();
	}

	@Dado("que eu acesso a url de criação de lista de tarefas")
	public void queEuAcessoAUrlDeCriaçãoDeListaDeTarefas() {
		//
		step("Acessar a URL do Todo-List");
		//
		getTodoListPG().getTodoUrl();
	}

	@Quando("eu inserir os itens na lista de tarefas")
	public void euInserirOsItensNaListaDeTarefas(DataTable listaItens) {
		//
		step("Inserir itens no Todo-List");
		//
		GlobalUtils.getDataTable(listaItens)
			.stream()
			.forEach(item -> {
				getTodoListPG().getTxtNewTodo().sendKeys(item);
				this.wait.until(
					ExpectedConditions.visibilityOf(
						getTodoListPG().getTxtNewTodo()
					)
				);
			//
			WebDriverUtils.sleepInSeconds(1);
			//
			getTodoListPG().getTxtNewTodo().sendKeys(Keys.ENTER);
			//
		});
	}

	@Então("os dados incluídos deverão estar dispostos na lista")
	public void osDadosIncluídosDeverãoEstarDispostosNaLista() {
		//
		getTodoListPG().getLblTodoList()
			.stream()
			.forEach(item -> {
				System.out.println(
					String.format(
						"((Todo List: %s))",
						item.getText()
					)
				);
			});
		//
	}

	@Então("ao clicar no botão de exclusão, os itens deverão ser excluídos")
	public void aoClicarNoBotãoDeExclusãoOsItensDeverãoSerExcluídos() {
		//
		this.wait.until(
			ExpectedConditions.visibilityOf(
				getTodoListPG().getUlTodolist()
			)
		);
		//
		var elementsList = getTodoListPG().getBtnDeleteTodoItem();
				
		for (int i = 0; i < elementsList.size(); i++) {
			//
			WebDriverUtils.sleepInSeconds(1);
			//
			this.wait.until(
				ExpectedConditions.visibilityOf(
					this.driver
					.findElements(
						By.xpath("//div[@*='view']")
					)
					.get(0)
				)
			);
			//
			new Actions(this.driver)
				.moveToElement(
					WebDriverUtils
						.getDriverManager()
						.getDriver()
						.findElements(
							By.xpath("//*[@*='view']")
						)
						.get(0)
					//
				)
				.perform();
			//
			this.driver.findElements(
				By.xpath("//*[@*='view']/button")
			)
			.get(0)
			.click();
			//
		}
	}

	@Então("ao clicar no botão Toggle All, todos itens deverão ser selecionados")
	public void aoClicarNoBotãoToggleAllTodosItensDeverãoSerSelecionados() {
		getTodoListPG().getCkbToggleAll().click();
	}

	@Então("ao clicar no botão Clear Completed, todos os itens deverão ser excluídos")
	public void aoClicarNoBotãoClearCompletedTodosOsItensDeverãoSerExcluídos() {
		getTodoListPG().getBtnClearCompleted().click();
		//
		this.driver.manage().deleteAllCookies();
	}
}