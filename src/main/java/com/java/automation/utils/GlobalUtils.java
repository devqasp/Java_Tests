package com.java.automation.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.java.automation.api.model.ResponseClass;

import io.cucumber.datatable.DataTable;

/**
 * @author Niky Lima
 * @info   Classe que contém uma série de métodos para a adequação e validação de
 * 		   dados. Aqui, podemos utilizar e implementar funções facilitadoras, que
 * 		   vão nos auxiliar no tratamento do código.
 */
public class GlobalUtils {

	static JSONParser getJSONParser() {
		return new JSONParser();
	}

	/**
	 * <p>De: String Para: HashMap<String, Object></p>
	 * Transforma uma String em um HashMap<String, Object>.
	 * @param response - String no formato de resposta de um serviço (JSON).
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> getHashMapFromResponse(String response) {

		HashMap<String, Object> result = null;
		//
		try {
			//
			result = new ObjectMapper()
				.readValue(
					response,
					HashMap.class
				);
			//
		} catch (JsonProcessingException ex) {
			//
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>De: String Para: LinkedList<> (String)</p>
	 * Tratamento para transformar a resposta de um serviço
	 * em formato String, em uma lista de String.
	 */
	public static List<String> getResponse() {

		String[] strArr = {};

		List<String> linkedList = new LinkedList<>();

		strArr = new Gson()
			.toJson(
				getHashMapFromResponse(
					ResponseClass.getResponse()
				)
				.entrySet()
				.parallelStream()
				.map(v -> v.getValue())
				.collect(
					Collectors.toList()
				)
			).split(",");
		//
		for (String strValue : strArr) {
			linkedList.add(removeBracketsAndDoubleQuotes(strValue));
		}
		//
		return linkedList;
	}

	/**
	 * Valida o HTTP Status Code da resposta de um serviço.
	 */
	public static void assertHttpStatusCode(int httpStatusCode) {

		Assertions.assertEquals(
			ResponseClass.getHttpStatusCode() /* Actual Value */,
				httpStatusCode /* Expected value */,
				String.format(
					"¡O HTTP Status Code atual (%s) é diferente do esperado (%s)!",
					ResponseClass.getHttpStatusCode(),
					httpStatusCode
				)
			);
		//
	}

	/**
	 * Gera uma String, à partir de um Objeto Map (SortedMap<String, String>),
	 * que reflete o formato de um arquivo JSON.
	 */
	@SuppressWarnings("serial")
	public static String generateStringJSONFormat(SortedMap<String, String> sortedMap) {

		Gson gson = new Gson();
		Type gsonType = new TypeToken<>() {}.getType();
		return gson.toJson(sortedMap, gsonType);
	}

	/**
	 * <p>De: JSONObject Para: List<> (String)</p>
	 * Retorna um valor específico da resposta de um serviço
	 * pelo seu índice, no formato de lista de String.
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getValueByIndexFromResponse(String key) {

		return (List<String>)
			new JSONObject(
				getHashMapFromResponse(
					ResponseClass.getResponse()
				)
			).get(key);
		//
	}

	/**
	 * Verifica se um valor específico existe na resposta do serviço.
	 * **A resposta de serviço é convertida em um HashMap para a comparação.**
	 */
	public static void compareValueResponseString(String value) {

		Object ObjValue = new ArrayList<>();

		int itr = 0;

		HashMap<String, Object> result =
			getHashMapFromResponse(ResponseClass.getResponse());

		Set<String> entrySet = result.keySet();
		for (String key : entrySet) {
			ObjValue = result.get(key);
			String jsonString = new Gson().toJson(ObjValue);
			String[] values = jsonString.split(",");

			for (int i = 0; i < values.length; i++) {
				if (removeBracketsAndDoubleQuotes(values[i]).equals(value)) {
					System.out.println(
						String.format(
							"Resultado | %s |",
							values[i]
						)
					);
					itr += 1;
					/*
					 * Assertion.assertTrue(removeBracketsAndDoubleQuotes(values[i]).equals(value),
					 * String.format("The value %s is not equal %s!", values[i], value)); break;
					 */
				}
			}
		}
		//
		if (itr == 0)
			Assertions.assertEquals(
				itr /* Actual Value */,
				1 /* Expected value */,
				String.format(
					"¡O valor comparado %s não existe na resposta do JSON!",
					value
				)
			);
		//
	}

	/**
	 * <p>De: String Para: Map<String, Object></p>
	 * Gera um Map<String, Object> à partir de uma String.
	 * Obs: Essa função foi testada com a resposta de API,
	 * em formato String (asPrettyString - RestAssured).
	 */
	public static Map<String, Object> generateMapFromString(String strValue) {
		//
		String[] strArrayToMap = 
			GlobalUtils
				.removeBracketsAndDoubleQuotes(strValue)
				.split(",");
		//
		return Arrays
			.asList(strArrayToMap)
			.parallelStream()
			.map(str -> str.split(":", -1))
			.collect(
				Collectors
					.toMap(
						str -> str[0] != null || StringUtils
							.isNotBlank(str[0]) ? str[0].trim() : " ",
						str -> str[1] != null || StringUtils
							.isNotBlank(str[1]) ? str[1].trim() : " "
					)
				//
			);
		//
	}

	/**
	 * Executa uma busca na resposta da API, utilizando uma combinação (matcher).
	 * @null_or_zero groupBy - Caso o valor do parâmetro for null ou zero, retorna
	 *               		   toda a sequência da combinação.
	 * @Integer groupBy(int) - Retorna apenas o trecho com a expressão regular, da
	 *          			   posição desejada.
	 * @String matcher		 - A combinação que você quer encontrar na resposta da API.
	 */
	public static List<String> getMatchInResponse(Integer groupBy, String matcher) {

		List<String> mList = new LinkedList<>();
		//
		Matcher _matcher =
			Pattern.compile(matcher)
				.matcher(
					ResponseClass.getResponse()
				);
			//
		while (_matcher.find()) {
			mList.add(groupBy != null ?
				_matcher.group(groupBy) :
				_matcher.group()
			);
		}
		//
		return mList;
	}

	/**
	 * Realiza a pesquisa e valida uma palavra dentro da resposta de um serviço.
	 */
	public static void getWordInResponse(String word) {

		int count = 0;
		{
			Matcher matcher =
				Pattern
					.compile(word)
					.matcher(ResponseClass.getResponse());
			//
			while (matcher.find())
				count++;
		}
		//
		Assertions.assertTrue(
			count > 0,
			String.format(
				"¡A palavra pesquisada (%s) não foi encontrada no resposta do serviço!",
				word
			)
		);
	}

	/**
	 * Transforma um DataTable em uma lista de String e retorna a primeira posição.
	 */
	public static String getFirstIndexDataTable(DataTable dataTable) {

		return (String) dataTable.asList(String.class).get(0);
	}

	/**
	 * <p>De: DataTable Para: List<> (String)</p>
	 * Transforma um Cucumber DataTable em uma lista de String.
	 */
	public static List<String> getDataTable(DataTable dataTable) {

		int itr = 0;

		List<String> strList = dataTable.asList(String.class);

		for (String strData : strList) {
			itr += 1;
			System.out.println(
				String.format(
					"Resultado (%d): %s.",
					itr,
					strData
				)
			);
		}
		//
		return strList.subList(1, strList.size());
	}

	/**
	 * **Primeira forma de validação** Valida os valores de duas listas, pondo-os em
	 * ordem alfabética e realizando a comparação.
	 */
	public static void assertResponseList(List<String> listA, List<String> listB) {

		int itr = 0, lstASize = listA.size();

		for (String strA : listA) {
			for (String strB : listB) {
				if (strA.equals(strB)) {
					itr += 1;
					System.out.println(
						String.format(
							"--- VALIDADO --- Resultado Massa (%d): %s | Resultado JSON (%d): %s.",
							itr,
							strA,
							itr,
							strB
						)
					);
				}
			}
		}

		Assertions.assertEquals(
			itr /* Actual Value */,
			lstASize /* Expected value */,
			String.format(
				"¡A lista / massa do teste apresenta uma diferença ao ser comparada com a resposta do JSON! Massa: %s | JSON: %s",
				itr,
				lstASize
			)
		);
	}

	/**
	 * **Segunda forma de validação** Valida os valores de duas listas,
	 * pondo-os em ordem alfabética e realizando a comparação.
	 */
	public static void assertResponseListReduced(List<String> listA, List<String> listB) {

		listA = putInAlphabeticalOrder(listA);

		listB = putInAlphabeticalOrder(listB);

		for (int i = 0; i < listA.size(); i++) {
			Assertions.assertEquals(
				listA.get(i) /* Actual Value */,
				listB.get(i) /* Expected value */,
				String.format(
					"¡O valor esperado (%s) não é o mesmo que o valor atual (%s)!",
					listA.get(i),
					listB.get(i)
				)
			);
		}
	}

	/**
	 * **Terceira forma de validação** Valida os valores de duas listas, pondo-os em
	 * ordem alfabética e realizando a comparação.
	 */
	public void assertIterableEqualsResponseListReduced(List<String> listA, List<String> listB) {

		listA = putInAlphabeticalOrder(listA);

		listB = putInAlphabeticalOrder(listB);

		Assertions.assertIterableEquals(
			listA,
			listB,
			"-- ¡O valor atual não é igual ao valor comparado / esperado! --"
		);
	}

	/**
	 * <p>Remove colchetes e aspas duplas de uma String.</p>
	 */
	public static String removeBracketsAndDoubleQuotes(String strValue) {

		return StringUtils.isEmpty(strValue) || strValue.isEmpty() || strValue.isBlank() || strValue == null ? strValue
				: strValue.replaceAll("[\\p{Ps}\\p{Pe}]", "").replaceAll("\"", "");
	}

	/**
	 * <p>Remove espaços em branco e tabulação.</p>
	 * Indicado para remover inconsistência do tipo acima citado,
	 * encontradas nos aquivos de propriedades.
	 */
	public static String removeWhiteSpacesAndTabulation(String strValue) {

		return StringUtils
			.isEmpty(strValue) || strValue.isEmpty() ||
		 // strValue.isBlank() ||
			strValue == null ? strValue : strValue.replaceFirst("\\s++$", "");
	}

	/**
	 * Verifica se um valor (String) esperado consta no conteúdo de um texto.
	 * @param text          - O texto onde se deseja aplicar a pesquisa.
	 * @param expectedValue - Valor (String) que deseja encontrar na pesquisa.
	 */
	public static void assertExpectedValueInText(String text, String expectedValue) {

		assertTrue(StringUtils
			.contains(
				text,
				expectedValue
			),
			String.format(
				"¡O valor experado (%s) não existe no conteúdo do texto: (%s)!",
				expectedValue,
				text
			)
		);
	}

	/**
	 * Verifica se um ou mais valores (String) existem no conteúdo de um texto.
	 * @param text           - O texto onde se deseja aplicar a pesquisa.
	 * @param expectedValues - Valores (String[]) que deseja encontrar na pesquisa.
	 */
	public static void assertExpectedValuesInText(String text, String... expectedValues) {
		
		assertTrue(
			Arrays
				.asList(expectedValues)
				.parallelStream()
				.allMatch(text::contains),
			String.format(
				"¡O valor experado (%s) não existe no conteúdo do texto: (%s)!",
				expectedValues[0],
				text
			)
		);
	}

	/**
	 * Execução de comandos no prompt do Windows.
	 */
	public static void cmdCommand(String... args) {

		if (args.length < 1) {
			System.out.println("IN USE: Windows Prompt - Command Line via Java application...");
			System.exit(1);
		}
		//
		try {
			Runtime rt = Runtime.getRuntime();
			System.out.println(
				String.format(
					"Command %s %s %s",
					args[0],
					args[1],
					args[2]
				)
			);
			//
			Process proc = rt.exec(
				String.format(
					"%s %s %s",
					args[0],
					args[1],
					args[2]
				)
			);
			//
			StreamGobbler errorGobbler =
				new StreamGobbler(
					proc.getErrorStream(),
					"ERROR"
				);
			//
			StreamGobbler outputGobbler =
				new StreamGobbler(
					proc.getInputStream(),
					"OUTPUT"
				);
			//
			errorGobbler.start();
			outputGobbler.start();
			int exitVal = proc.waitFor();
			System.out.println(
				String.format(
					"ExitValue: %s",
					exitVal
				)
			);
			//
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Organiza uma lista em ordem alfabética.
	 */
	public static List<String> putInAlphabeticalOrder(List<String> lstString) {

		String temp;

		String[] stockArr = new String[lstString.size()];

		stockArr = lstString.toArray(stockArr);

		for (int i = 0; i < stockArr.length; i++) {
			for (int j = i + 1; j < stockArr.length; j++) {
				if (stockArr[i].compareTo(stockArr[j]) > 0) {
					temp = stockArr[i];
					stockArr[i] = stockArr[j];
					stockArr[j] = temp;
				}
			}
		}
		return Arrays.asList(stockArr);
	}

	/**
	 * Retorna a data atual formatada - Máscara dd.MM.yyyy_HH.mm.ss.
	 */
	public static String generateReferenceDate() {

		return new
				SimpleDateFormat(
					"dd.MM.yyyy_HH.mm.ss"
				)
				.format(
					Calendar
						.getInstance()
						.getTime()
				);
			//
		//
	}
}