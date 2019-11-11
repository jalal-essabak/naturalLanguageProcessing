package ma.ensao.pfa.util;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	/**
	 * Properties
	 */

	/**
	 * Id of the sentence in a paragraph
	 */
	private int id;

	/**
	 * The actual sentence this instance represents.
	 */
	private String sentence;

	/**
	 * List of all tokens contained in this sentence
	 */
	private List<Token> wordsList;

	/**
	 * Parameterized constructor
	 * @param sentence
	 */
	public Sentence(String sentence) {
		this.sentence = sentence;
	}

	/**
	 * default constructor
	 */
	public Sentence() {
		this.sentence = "";
	}

	/**
	 * Breaks the sentence into tokens
	 * 
	 * @return List of all tokens
	 */
	public List<Token> tokensList() {
		wordsList = new ArrayList<Token>();

		// adding whitespace before & after special characters in order to split the
		// sentence

		sentence = sentence.replaceAll("([,])+", " , ");
		sentence = sentence.replaceAll("([.])", " . ");
		sentence = sentence.replaceAll("([?])+", " ? ");
		sentence = sentence.replaceAll("([!])+", " ! ");
		sentence = sentence.replaceAll("([(])+", " ( ");
		sentence = sentence.replaceAll("([)])+", " ) ");
		sentence = sentence.replaceAll("([’])+", "’ ");
		sentence = sentence.replaceAll("(['])+", "' ");
		sentence = sentence.replaceAll("([-])+", " - ");
		sentence = sentence.replaceAll("([;])+", " ;");
		sentence = sentence.replaceAll("([:])+", " : ");
		sentence = sentence.replaceAll("\"", "\" ");
		sentence = sentence.replaceAll("«", " « ");
		sentence = sentence.replaceAll("»", " » ");

		// Splitting the sentence using whitespace regex

		String[] words = sentence.split("[\\s]+");

		for (String word : words) {
			wordsList.add(new Token(word.trim()));
		}

		return wordsList;
	}

	/**
	 * ACCESSORS
	 *
	 */
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getSentence() {
		return sentence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
