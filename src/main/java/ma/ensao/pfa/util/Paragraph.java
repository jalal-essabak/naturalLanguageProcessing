package ma.ensao.pfa.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Paragraph {
	/**
	 * Properties
	 */

	/**
	 * Id of the paragraph in a text
	 */
	private int id;

	/**
	 * The actual paragraph string this instance represents.
	 */

	private String paragraph;

	/**
	 * List of all sentences contained in this paragraph
	 * 
	 */
	private List<Sentence> sentencesList;

	/**
	 * Parameterized constructor
	 */

	public Paragraph(String paragraph) {
		this.paragraph = paragraph;
	}

	/**
	 * default constructor
	 */
	public Paragraph() {
		this.paragraph = "";
	}

	/**
	 * Breaks the paragraph into sentences
	 * 
	 * @return List of all sentences
	 */

	public List<Sentence> sentencesList() {

		sentencesList = new ArrayList<Sentence>();

		BreakIterator bi = BreakIterator.getSentenceInstance(Locale.FRANCE);
		bi.setText(paragraph);
		int start = 0;
		int end = 0;
		while ((end = bi.next()) != BreakIterator.DONE) {
			sentencesList.add(new Sentence(paragraph.substring(start, end)));
			start = end;
		}
		return sentencesList;

	}

	/**
	 * ACCESSORS
	 *
	 */
	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
