package ma.ensao.pfa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Text {
	/**
	 * Properties
	 */

	private String text;

	/**
	 * List of all paragraphs contained in this text
	 * 
	 */
	private List<Paragraph> paragraphesList;

	/**
	 * Parameterized constructor
	 */

	public Text(String text) {

		this.text = text;

	}

	/**
	 * Default constructor
	 */

	public Text() {

		this.text = "";

	}

	/**
	 * Breaks the text into paragraphs
	 * 
	 * @return List of all paragraphs
	 */

	public List<Paragraph> paragraphesList() {

		paragraphesList = new ArrayList<Paragraph>();

		String paragraphRegexSplit = "(?<=(\\r\\n|\\r|\\n))([ \\\\t]*$)+";
		String[] paragraphTab = Pattern.compile(paragraphRegexSplit, Pattern.MULTILINE).split(text);

		for (String para : paragraphTab) {
			paragraphesList.add(new Paragraph(para.trim()));
		}
		return paragraphesList;

	}

	/**
	 * ACCESSORS
	 *
	 */

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
