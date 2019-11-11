package ma.ensao.pfa.util;

public class Token {
	/**
	 * Properties
	 */

	/**
	 * Id of the word in a sentence
	 */
	private int id;

	/**
	 * The actual natural language word this instance represents.
	 */
	private String token;

	/**
	 * Parts of speech, or “Lexical categories”, for this word.
	 */
	private String pos;

	/**
	 * converting a word to its base form "racine du mot"
	 */
	private String lemma;
	private String gender;
	private String number;
	private String tense;

	/**
	 * Parameterized constructor to initializes all fields except the id when
	 * creating a new token.
	 */

	public Token(String token, String gender, String lemma, String number, String pos, String tense) {
		this.gender = gender;
		this.lemma = lemma;
		this.token = token;
		this.number = number;
		this.pos = pos;
		this.tense = tense;
	}	

	public Token(String entity) {
		this.token = entity;
	}

	/**
	 * ACCESSORS
	 *
	 */

	public String getToken() {
		return token;
	}

	public void setToken(String entity) {
		this.token = entity;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTense() {
		return tense;
	}

	public void setTense(String tense) {
		this.tense = tense;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
