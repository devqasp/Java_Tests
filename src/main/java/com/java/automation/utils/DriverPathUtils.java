package com.java.automation.utils;

import java.io.File;
import java.util.stream.Stream;

import com.java.automation.sysinfo.SystemInfo;
import com.java.automation.web.browser.factory.BrowserProperties;

/**
 * @author Niky Lima
 * @info Classe que contém os métodos responsáveis por realizar a leitura de
 *       diretórios e arquivos de driver.
 */
public class DriverPathUtils {

	/**
	 * Método criado para definir qual é a extensão do driver, à partir do Sistema
	 * Operacional onde a automação Web-UI está sendo executada.
	 */
	public static String getFileExtensionUsingOSName() {

		String osName = new SystemInfo().getOsName().toLowerCase();
		//
		return osName.contains("linux") ? "" : ".exe";
	}

	/**
	 * Esse método é responsável por devolver o caminho de um driver, com base no
	 * nome do Sistema Operacional onde está sendo executada a automação Web-UI.
	 * 
	 * @info No parâmetro, é indicado o resource path de onde você deseja buscar o
	 *       arquivo. Ex: src/test/resources/pasta_do_arquivo
	 */
	public static String getFilePathUsingOSName(String resourcePath) {

		String
			osName		   = new SystemInfo().getOsName().toLowerCase(),
			browserName	   = new BrowserProperties().getBrowserName().toLowerCase(),
			browserVersion = String.format(
				"version-%s",
				new BrowserProperties().getBrowserVersion()
			);
		//
		return new File(
				String.format(
					"%s/%s/%s/%s/",
					resourcePath,
					osName.contains("linux") && !browserName.contains("iexplorer") ? "linux" : "windows",
					browserName,
					browserVersion
				)
			)
			.getAbsolutePath();
		//
	}

	/**
	 * Esse método é responsável por devolver o caminho absoluto de um driver, com
	 * base no nome do Sistema Operacional onde está sendo executada a automação
	 * Web-UI.
	 * 
	 * @info No parâmetro, é indicado o resource path de onde você deseja buscar o
	 *       arquivo. O driver retornado é o primeiro dentro da lista de versões que
	 *       podem existir no diretório de drivers. Ex: Se no diretório
	 *       src/main/resources/drivers/windows/chrome/version... possuir mais de
	 *       uma versão de driver, o escolhido será o sempre o primeiro.
	 */
	public static String getDriverPathUsingOSName(String resourcePath) {

		String
			osName		   = new SystemInfo().getOsName().toLowerCase(),
			browserName	   = new BrowserProperties().getBrowserName().toLowerCase(),
			browserVersion = String.format(
				"version-%s",
				new BrowserProperties().getBrowserVersion()
			);
		//
		File[] driverFiles =
			new File(
				String.format(
					"%s/%s/%s/%s/", resourcePath,
					osName.contains("linux") && !browserName.contains("iexplorer") ? "linux" : "windows",
					browserName,
					browserVersion
				)
			)
			.listFiles();
		//
		return Stream.of(driverFiles)
			.filter(f -> f.length() > 0)
			.findFirst()
			.get()
			.getAbsolutePath();
	}

	/**
	 * Esse método configura a versão do browser atráves do Java Command-Line.
	 * 
	 * @info1 - No parâmetro, é indicado o resource path de onde você deseja buscar o
	 *			arquivo. Ex: -Dbwr_version=1.2.3
	 */
	public static String getDvrPathUsingOSNameByBwrVersion(String resourcePath) {

		String
			osName		  = new SystemInfo().getOsName().toLowerCase(),
			browserName	  = new BrowserProperties().getBrowserName().toLowerCase(),
				/**
				 * @info2 - Trecho que retorna, via Java Command-Line, a versão do browser a ser
				 *			utilizada...
				 */
			browserVersion = JavaCMDLineClass.getBrowserVersion();
		//
		File[] driverFiles =
			new File(
				String.format(
					"%s/%s/%s/%s/", resourcePath,
					osName.contains("linux") && !browserName.contains("iexplorer") ? "linux" : "windows",
					browserName,
					browserVersion
				)
			)
			.listFiles();
		//
		return Stream.of(driverFiles)
			.filter(f -> f.length() > 0)
			.findFirst()
			.get()
			.getAbsolutePath();
	}
}