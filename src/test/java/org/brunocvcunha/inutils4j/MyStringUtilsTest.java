/**
 * Copyright (C) 2014 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.inutils4j;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.brunocvcunha.inutils4j.MyStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Default tests.
 *
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class MyStringUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testeLines() {
		String content = "Linha 1\r\nLinha 2\r\nLinha 3";
		assertEquals("Linha 1", MyStringUtils.getLine(content, 1));
		assertEquals("Linha 2", MyStringUtils.getLine(content, 2));
		assertEquals("Linha 3", MyStringUtils.getLine(content, 3));
	}

	@Test
	public void testFirstLine() {
		assertEquals("Bruno", MyStringUtils.firstLine("Bruno"));
		assertEquals("Bruno", MyStringUtils.firstLine("Bruno\r\nLOL\nLOLZ"));
		assertEquals("HEHEHE", MyStringUtils.firstLine("HEHEHE\nLOL\nLOLZ"));

	}

	@Test
	public void testeLines2() {
		String content = "Linha 1\nLinha 2\nLinha 3";
		assertEquals("Linha 1", MyStringUtils.getLine(content, 1));
		assertEquals("Linha 2", MyStringUtils.getLine(content, 2));
		assertEquals("Linha 3", MyStringUtils.getLine(content, 3));
	}

	@Test
	public void testeNumbers() {
		assertTrue(MyStringUtils.isInteger("1"));
		assertTrue(MyStringUtils.isInteger("123456"));
		assertTrue(MyStringUtils.isInteger("010101"));
		assertFalse(MyStringUtils.isInteger("a"));

	}

	@Test
	public void testOccurences() {
		assertEquals(3, StringUtils.countMatches("testee xteste tteste", "teste"));
		assertEquals(2, StringUtils.countMatches("estee xteste tteste", "teste"));
	}

    @Test
    public void testJapanese() {
        assertTrue(MyStringUtils.hasJapaneseCharacter("日本語ワード"));
    }
    @Test
    public void testNonJapanese() {
        assertFalse(MyStringUtils.hasJapaneseCharacter("japanese word"));
        assertFalse(MyStringUtils.hasJapaneseCharacter("my name is inutils"));
    }

       
    @Test
    public void testChinese() {
        assertTrue(MyStringUtils.hasChineseCharacter("中國字"));
        assertTrue(MyStringUtils.hasChineseCharacter("我的字是沒用的"));
    }
    @Test
    public void testNonChinese() {
        assertFalse(MyStringUtils.hasChineseCharacter("chinese word"));
        assertFalse(MyStringUtils.hasChineseCharacter("my name is inutils"));
    }

       
	@Test
	public void testInsertSubstring() {
		assertEquals("BRUNO", MyStringUtils.insertSubString("BRNO", "U", 2));
		assertEquals("LOL ME", MyStringUtils.insertSubString("ME", "LOL ", 0));

		assertEquals("TESTECABULOSO", MyStringUtils.insertSubString("TULOSO", "ESTECAB", 1));

	}

	@Test
	public void testeExtractSuffix() {
		assertArrayEquals(new String[] { "c-lbl-", "15" }, MyStringUtils.extractSuffixInt("c-lbl-15"));
		assertArrayEquals(new String[] { "c-lbl-", "015" }, MyStringUtils.extractSuffixInt("c-lbl-015"));
		assertArrayEquals(new String[] { "c-lbl", "1231" }, MyStringUtils.extractSuffixInt("c-lbl1231"));
		assertArrayEquals(new String[] { "c-lbl", "0" }, MyStringUtils.extractSuffixInt("c-lbl"));

	}

	@Test
	public void testChars() {
		assertEquals("Teste", MyStringUtils.capitalize("teste"));
		assertEquals("NÃ£o", MyStringUtils.capitalize("nÃ£o"));
		assertEquals("Teste de validação", MyStringUtils.capitalize("teste de validação"));

		assertEquals("          ", MyStringUtils.spaces(10));
		assertEquals("", MyStringUtils.spaces(0));

		assertEquals("Bruno Candido Volpato da Cunha",
				MyStringUtils.trimClean("	 	 Bruno  Candido	Volpato 				\r\n		da Cunha"));

	}

	@Test
	public void testParseInt() {
		int[] arr = MyStringUtils.parseIntArray("1,5,6,11,17");

		assertEquals(arr[0], 1);
		assertEquals(arr[1], 5);
		assertEquals(arr[2], 6);
		assertEquals(arr[3], 11);
		assertEquals(arr[4], 17);
	}

	@Test
	public void testCharCount() {
		assertEquals(MyStringUtils.charCount("/Teste/", '/'), 2);
		assertEquals(MyStringUtils.charCount("Teste/Teste", '/'), 1);
		assertEquals(MyStringUtils.charCount("/Teste", '/'), 1);
		assertEquals(MyStringUtils.charCount("Teste/", '/'), 1);
		assertEquals(MyStringUtils.charCount("Teste/de/Barras/", '/'), 3);
		assertEquals(MyStringUtils.charCount("Teste///de/Barras/", '/'), 5);
	}

	@Test
	public void testInterval() {
		long second = 1000;
		long minute = second * 60;
		long hour = minute * 60;

		assertEquals(MyStringUtils.intervalToString(second), "1s 0ms");
		assertEquals(MyStringUtils.intervalToString(minute * 2), "2m 0s 0ms");
		assertEquals(MyStringUtils.intervalToString(minute + (second * 15)), "1m 15s 0ms");
		assertEquals(MyStringUtils.intervalToString(hour), "1h 0m 0s 0ms");

		assertEquals(MyStringUtils.intervalToString(hour + (minute * 33) + (second * 59)), "1h 33m 59s 0ms");

		assertEquals(MyStringUtils.intervalToString(hour * 47), "1d 23h 0m 0s 0ms");
		assertEquals(MyStringUtils.intervalToString(hour * 48), "2d 0h 0m 0s 0ms");
	}

	@Test
	public void testBlank() {
		assertTrue(MyStringUtils.isBlank(""));
		assertTrue(MyStringUtils.isBlank(" "));
		assertFalse(MyStringUtils.isBlank("DUMMY"));
	}

	@Test
	public void testAcentuacao1() {

		assertEquals(
				"E importante questionar o quanto o consenso sobre a necessidade de qualificacao maximiza as possibilidades por conta do orcamento setorial.",
				MyStringUtils
						.removeAccents("É importante questionar o quanto o consenso sobre a necessidade de qualificação maximiza as possibilidades por conta do orçamento setorial."));

		assertEquals("Laercio", MyStringUtils.removeAccents("Laércio"));
		assertEquals("Ordem de Producao", MyStringUtils.removeAccents("Ordem de Produção"));
		assertEquals("Util", MyStringUtils.removeAccents("Útil"));
		assertEquals("INUTIL", MyStringUtils.removeAccents("INÚTIL"));
		assertEquals("util", MyStringUtils.removeAccents("útil"));
		assertEquals("obrigatoria", MyStringUtils.removeAccents("obrigatória"));
		assertEquals("nivel", MyStringUtils.removeAccents("nível"));
		assertEquals("teste de acentuacao", MyStringUtils.removeAccents("teste de acentuação"));
		assertEquals("TESTE DE ACENTUACAO", MyStringUtils.removeAccents("TESTE DE ACENTUAÇÃO"));
		assertEquals("TESTE DE ACENTUACAO AVANCADO 2.0",
				MyStringUtils.removeAccents("TESTE DE ACENTUAÇÃO AVANÇADO 2.0"));
		assertEquals("quociente de inteligencia", MyStringUtils.removeAccents("qüociente de inteligencia"));
		assertEquals("QUOCIENTE DE INTELIGENCIA", MyStringUtils.removeAccents("QÜOCIENTE DE INTELIGENCIA"));

	}

	@Test
	public void testAcentuacao2() {
		assertEquals(
				"O cuidado em identificar pontos criticos na valorizacao de fatores subjetivos assume importantes posicoes no estabelecimento dos relacionamentos verticais entre as hierarquias.",
				MyStringUtils
						.removeAccents("O cuidado em identificar pontos críticos na valorização de fatores subjetivos assume importantes posições no estabelecimento dos relacionamentos verticais entre as hierarquias."));

	}

	@Test
	public void testAcentuacao3() {
		assertEquals(
				"Nunca e demais lembrar o peso e o significado destes problemas, uma vez que a crescente influencia da midia obstaculiza a apreciacao da importancia das direcoes preferenciais no sentido do progresso.",
				MyStringUtils
						.removeAccents("Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a crescente influência da mídia obstaculiza a apreciação da importância das direções preferenciais no sentido do progresso."));

	}

	@Test
	public void testHas() {
		assertTrue(MyStringUtils.hasLetter("teste"));
		assertTrue(MyStringUtils.hasLetter("TE!@#$%Ã‚Â¨&*()STE"));
		assertTrue(MyStringUtils.hasLetter("123teste123"));
		assertTrue(MyStringUtils.hasLetter("12345s12345"));
		assertTrue(MyStringUtils.hasLetter("s1234567890"));
		assertTrue(MyStringUtils.hasLetter("1234567890S"));
		assertTrue(MyStringUtils.hasLetter("S1234567890"));
		assertTrue(MyStringUtils.hasLetter("1234567890s"));
		assertTrue(MyStringUtils.hasLetter("__s1234567890"));
		assertTrue(MyStringUtils.hasLetter("1234__567890S"));
		assertTrue(MyStringUtils.hasLetter("S1_234567890"));
		assertTrue(MyStringUtils.hasLetter("123456_7890s99"));

		assertFalse(MyStringUtils.hasLetter("_"));
		assertFalse(MyStringUtils.hasLetter("9"));
		assertFalse(MyStringUtils.hasLetter("1234567890"));
		assertFalse(MyStringUtils.hasLetter("!@#$%Ã‚Â¨&*()"));

	}

	@Test
	public void testCollections1() {
		List<String> a = new ArrayList<String>();
		a.add("oi");
		List<String> b = new ArrayList<String>();
		b.add("oi");

		Collection<String> c = MyStringUtils.mixLists(a, b);
		assertEquals(c.size(), 2);
	}

	@Test
	public void testCollections2() {
		Set<String> a = new TreeSet<String>();
		a.add("oi");
		Set<String> b = new TreeSet<String>();
		b.add("oi");

		Collection<String> c = MyStringUtils.mixLists(a, b);
		assertEquals(c.size(), 2);
	}

	@Test
	public void testCollections3() {
		List<String> list = MyStringUtils.asListLines("Bruno\nCandido\r\nVolpato\r\nda\nCunha");

		assertEquals(list.get(0), "Bruno");
		assertEquals(list.get(1), "Candido");
		assertEquals(list.get(2), "Volpato");
		assertEquals(list.get(3), "da");
		assertEquals(list.get(4), "Cunha");
	}

	@Test
	public void testTruncate() {
		assertEquals(MyStringUtils.truncate("Bruno Candido Volpato da Cunha", 5), "Bruno");
		assertEquals(MyStringUtils.truncate("Bruno Candido Volpato da Cunha", 6), "Bruno ");
		assertEquals(MyStringUtils.truncate("LOL", 6), "LOL");
	}

	@Test
	public void testHash() {
		assertEquals("b2d72d942794a632b81d1fef3111f1fd", MyStringUtils.md5hex("Bruno Candido Volpato da Cunha"));
		assertEquals("b6f426763c8030fe53a86072bb7d1e5756620c84",
				MyStringUtils.sha1hex("Bruno Candido Volpato da Cunha"));
	}
	
	
	@Test
	public void testCheckChars() {
		
		//abcdefghijklmnopqrstuvwxyz0123456789@.
		assertTrue(MyStringUtils.hasInvalidChar("b", "acd"));
		assertFalse(MyStringUtils.hasInvalidChar("a", "acd"));
		
		assertFalse(MyStringUtils.hasInvalidChar("brunocvcunha@gmail.com", "abcdefghijklmnopqrstuvwxyz0123456789@."));
		assertTrue(MyStringUtils.hasInvalidChar("Bruno Candido Volpato da Cunha", "abcdefghijklmnopqrstuvwxyz0123456789@. "));
		assertFalse(MyStringUtils.hasInvalidChar("Bruno Candido Volpato da Cunha", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@. "));
		
	}
}
