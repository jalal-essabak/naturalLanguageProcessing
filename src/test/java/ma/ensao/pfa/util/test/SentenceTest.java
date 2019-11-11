package ma.ensao.pfa.util.test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ma.ensao.pfa.util.*;

public class SentenceTest {
	List<Token> tokensList;
	Sentence sentence;

	@Before
	public void setUp() throws Exception {
		tokensList = new ArrayList<Token>();
		sentence = new Sentence();
	}

	@After
	public void tearDown() throws Exception {
		tokensList = null;
		sentence=null;
	}

	@Test

	public void testwordsList() {
		String phrase1 = "Paris est la capitale de la France.";
		String phrase2 = "Freud : fondateur de la psychanalyse, titulaire d'une paternité déjà légendaire ; l'homme n'est plus séparable de la science à laquelle il a consacré sa vie entière";
		String phrase3 = "\"Qu'est-ce qu'il y a encore?\"";
		/*
		 * Premiere phrase;
		 * Paris est la capitale de la France.
		 */
		sentence.setSentence(phrase1);
		tokensList = sentence.tokensList();

		assertEquals("Paris", tokensList.get(0).getToken());
		assertEquals("est", tokensList.get(1).getToken());
		assertEquals("la", tokensList.get(2).getToken());
		assertEquals("capitale", tokensList.get(3).getToken());
		assertEquals("de", tokensList.get(4).getToken());
		assertEquals("la", tokensList.get(5).getToken());
		assertEquals("France", tokensList.get(6).getToken());
		assertEquals(".", tokensList.get(7).getToken());

		/*
		 * deuxieme phrase; Freud : fondateur de la psychanalyse, titulaire d'une
		 * paternité déjà légendaire ;
		 */
		sentence.setSentence(phrase2);
		tokensList = sentence.tokensList();

		assertEquals("Freud", tokensList.get(0).getToken());
		assertEquals(":", tokensList.get(1).getToken());
		assertEquals("fondateur", tokensList.get(2).getToken());
		assertEquals("de", tokensList.get(3).getToken());
		assertEquals("la", tokensList.get(4).getToken());
		assertEquals("psychanalyse", tokensList.get(5).getToken());
		assertEquals(",", tokensList.get(6).getToken());
		assertEquals("titulaire", tokensList.get(7).getToken());
		assertEquals("d'", tokensList.get(8).getToken());
		assertEquals("une", tokensList.get(9).getToken());
		assertEquals("paternité", tokensList.get(10).getToken());
		assertEquals("déjà", tokensList.get(11).getToken());
		assertEquals("légendaire", tokensList.get(12).getToken());
		assertEquals(";", tokensList.get(13).getToken());

		/*
		 * Troisieme sentence;
		 * "Qu'est-ce qu'il y a encore?"
		 */
		sentence.setSentence(phrase3);
		tokensList = sentence.tokensList();

		assertEquals("\"", tokensList.get(0).getToken());
		assertEquals("Qu'", tokensList.get(1).getToken());
		assertEquals("est", tokensList.get(2).getToken());
		assertEquals("-", tokensList.get(3).getToken());
		assertEquals("ce", tokensList.get(4).getToken());
		assertEquals("qu'", tokensList.get(5).getToken());
		assertEquals("il", tokensList.get(6).getToken());
		assertEquals("y", tokensList.get(7).getToken());
		assertEquals("a", tokensList.get(8).getToken());
		assertEquals("encore", tokensList.get(9).getToken());
		assertEquals("?", tokensList.get(10).getToken());
		assertEquals("\"", tokensList.get(11).getToken());

	}

}
