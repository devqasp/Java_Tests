package com.java.automation.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * @author Niky Lima
 * @info   Classe que contém métodos para conversão de arquivos em Objeto JSON,
 *         Mapa e String. Além de funções para criação, checagem e exclusão de
 *         diretórios.
 */
public class ManagerFileUtils {

	/**
	 * <p>De: Arquivo JSON Para: Objeto JSON</p>
	 * Converte um arquivo JSON em um objeto JSON.
	 * @param filePath - Caminho do arquivo no formato JSON.
	 */
	private static JSONObject getJSONObjectFromReader(String filePath) {

		JSONObject jsonObject = null;

		try (Reader reader = new FileReader(filePath)) {
			jsonObject = (JSONObject) GlobalUtils.getJSONParser().parse(reader);
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * <h1>
	 * 	<i>Código de Exemplo</i>
	 * </h1>
	 * Lê e exibe os valores, à partir de suas respectivas chaves,
	 * dispostas no arquivo example.json.
	 */
	@SuppressWarnings("unchecked")
	public static void readJsonFileExample() {

		JSONObject jsonObject = getJSONObjectFromReader(
			"src/test/resources/json-repo/example.json"
		);

		System.out.println(jsonObject);

		long id = (Long) jsonObject.get("Id");
		System.out.println(id);

		String name = (String) jsonObject.get("Name");
		System.out.println(name);

		String author = (String) jsonObject.get("Author");
		System.out.println(author);

		JSONArray msg = (JSONArray) jsonObject.get("Company List");

		Iterator<String> it = msg.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	/**
	 * <h1>
	 * 	<i>Código de Exemplo</i>
	 * </h1>
	 * Lê e exibe as chaves e seus valores,
	 * ambos contidos no arquivo example.json.
	 */
	@SuppressWarnings("unchecked")
	public static void printJsonObject() {

		JSONObject jsonObject = getJSONObjectFromReader(
			"src/test/resources/json-repo/example.json"
		);
		//
		jsonObject.keySet().forEach(
			key -> {
				Object value = jsonObject.get(key);
				System.out.println(
					String.format(
						"KEY: %s - VALUE: %s",
						key,
						value
					)
				);
			}
		);
	}

	/**
	 * Gera um Map<String, Object>, à partir da leitura de um JSONObject.
	 * @param strPath - O caminho do arquivo JSON com as chaves e 
	 * valores que deseja ler e converter.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParamsFromJson(String strPath) {

		return getJSONObjectFromReader(strPath);
	}

	/**
	 * Retorna os dados/valores de credencial, à partir da chave
	 * disposta no arquivo JSON custom_credentials_(env).json.
	 * @param keyValue - Valor da chave do arquivo JSON.
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> getCustomCredentiaFromJSON(String keyValue) {

		Map<String, Object> map = new LinkedHashMap<>();

		// JSONObject jsonObject =
		// getJSONObjectFromReader("src/test/resources/json-params-config/custom_credentials_dev.json");

		/**
		 * ** O valor aqui é indicado via VM args. **
		 */
		JSONObject jsonObject = getJSONObjectFromReader(
			String.format(
				"src/test/resources/proof-mass/json-credentials-params/custom_credentials_%s.json",
				JavaCMDLineClass.getEnv()
			)
		);
		//
		jsonObject.keySet().forEach(key -> {
			if (key.toString().equalsIgnoreCase(keyValue)) {
				map.put(key.toString(), jsonObject.get(key));
			}
		});
		//
		return map;
	}

	/**
	 * Gera um Map<String, Object>, devolvendo as chaves e seus
	 * respectivos valores, dispostos no arquivo JSON de Credenciais.
	 * @param keyValue - Valor da chave do arquivo JSON.
	 */
	public static Map<String, Object> generateMapFromCredetialsJSON(String keyValue) {
		//
		String strToStrArray = getCustomCredentiaFromJSON(keyValue).toString();
		//
		String[] strArrayToMap =
			GlobalUtils
				.removeBracketsAndDoubleQuotes(strToStrArray)
				.split("=")[1]
				.split(",");
		//
		return Arrays
			.asList(strArrayToMap)
			.stream().map(str ->
				str.split(":", -1)
			)
			.collect(Collectors
				.toMap(
					str -> str[0] != null || StringUtils.isNotBlank(
						str[0]) ? str[0].trim() : " ",
					str -> str[1] != null || StringUtils.isNotBlank(
						str[1]) ? str[1].trim() : " "
				)
			);
		//
	}

	/**
	 * Retorna um ou mais arquivos JSON especificados, filtrando-os pelo trecho
	 * inicial de seus nomes.
	 * @param directory    - Diretório onde os arquivos se encontram.
	 * @param charSequence - Trecho / palavra que pode ser encontrada no nome do arquivo.
	 * @param env          - Valor que diz respeito ao ambiente que está sendo 
	 * 						 executada a automação de testes.
	 * @param endsWith     - O treho final do nome de um ou mais arquivos.
	 */
	public static File[] getSpecificFilesFromDir(
		String directory,
		String charSequence,
		String env,
		String endsWith
	)
	{
		//
		return new File(
				directory
			)
			.listFiles(
				(dir, name) ->
					name.contains(charSequence) &&
					name.endsWith(
						String.format(
							"%s%s",
							env,
							endsWith
						)
					)
				//
			);
		//
	}

	/**
	 * <p>De: Arquivo JSON Para: Objeto JSON</p>
	 * Retorna um objeto JSON, à partir de um diretório de arquivos JSON.
	 * Realiza o fitro, utilizando startsWith.
	 * @param directory    - Diretório com os arquivos JSON.
	 * @param charSequence - Trecho / palavra que pode ser
	 * 						 encontrada no nome do arquivo.
	 */
	public static JSONObject getJSONObjectFromReaderUsingFilter(
		String directory,
		String charSequence
	)
	{
		//
		JSONObject jsonObject = null;
		//
		try (Reader reader = new FileReader(
			getSpecificFilesFromDir(
				directory,
				charSequence,
				JavaCMDLineClass.getEnv(),
				".json")[0]
			)
		)
		{
			jsonObject = (JSONObject) GlobalUtils.getJSONParser().parse(reader);
			//
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
		}
		//
		return jsonObject;
	}

	/**
	 * Gera um Map<String, Object>, devolvendo as chaves e seus respectivos valores,
	 * dispostos em um arquivo JSON de um diretório específico. Realiza o fitro,
	 * utilizando startsWith.
	 * @param directory    - Diretório com os arquivos JSON.
	 * @param charSequence - Trecho / palavra que pode ser encontrada no nome do
	 *                       arquivo.
	 * @param keyValue     - Valr da chave do arquivo JSON.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJSONFileUsingFilterAndKey(
		String directory,
		String charSequence,
		String keyValue
	)
	{
		//
		JSONObject jsonObject =
			getJSONObjectFromReaderUsingFilter(directory, charSequence);
		//
		Map<String, Object> map = new LinkedHashMap<>();
		//
		jsonObject.keySet().forEach(key -> {
			if (key.toString().equalsIgnoreCase(keyValue)) {
				map.put(key.toString(), jsonObject.get(key));
			}
		});
		//
		return Arrays
			.asList(GlobalUtils
				.removeBracketsAndDoubleQuotes(
					map.toString()
				)
				.split("=")[1]
				.split(",")
			)
			.stream()
			.map(
				str -> str.split(":", -1)
			)
			.collect(Collectors
				.toMap(
					str -> str[0] != null || StringUtils
						.isNotBlank(str[0]) ? str[0].trim() : " ",
					str -> str[1] != null || StringUtils
						.isNotBlank(str[1]) ? str[1].trim() : " "
				)
			);
		//
	}

	/**
	 * Retorna o valor de uma chave de URL contida no arquivo JSON urls.json.
	 */
	public static String getUrlFromJson(String url) {

		return (String) getJSONObjectFromReader("src/test/resources/json-repo/urls.json").get(url);
	}

	/**
	 * Verifica se existe determinado arquivo em um diretório.
	 */
	public static void checkFileExists(File file) {

		while (!file.exists()) {
			continue;
		}
		//
		System.out.println(
			String.format(
				"¡The %s exists / was created!",
				file.getName()
			)
		);
	}

	/**
	 * Verifica se o diretório passado no parâmetro existe.
	 * Caso não, o diretório é criado.
	 */
	public static File checkAndGenerateFilePath(String filePath) {

		File file = new File(filePath);

		if (!file.exists() && !file.isDirectory()) {

			try {
				System.out.println(
					String.format(
						"Was the DIR created? --%s--",
						file.mkdirs()
					)
				);
			//
			} catch (SecurityException ex) {
				ex.printStackTrace();
			}
		}
		//
		return file.getAbsoluteFile();
	}

	/**
	 * Exclui um determinado arquivo existente no diretório.
	 */
	public static void deleteFileIfExists(File file) {

		if (file.delete())
			System.out.println(
				String.format(
					"¡The %s has been deleted!",
					file.getName()
				)
			);
		else
			System.out.println(
				String.format(
					"¡Deletion of %s file failed!",
					file.getName()
				)
			);
		//
	}
}