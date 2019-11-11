package ma.ensao.pfa.util.test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ma.ensao.pfa.util.File;
import ma.ensao.pfa.util.Paragraph;
import ma.ensao.pfa.util.Sentence;

public class ParagraphTest {

	Paragraph paragraph;
	List<Sentence> sentencesList;

	@Before
	public void setUp() throws Exception {
		paragraph = new Paragraph();
		sentencesList = new ArrayList<Sentence>();
	}

	@After
	public void tearDown() throws Exception {
		paragraph = null;
		sentencesList = null;
	}

	@Test
	public void testSentencesList() throws IOException {
		
		String para=File.readFile("test-files/ParagraphTest.txt"); 
		paragraph.setParagraph(para);
		sentencesList = paragraph.sentencesList();

		assertEquals("Sigmund Freud, le père de la psychanalyse.\n",sentencesList.get(0).getSentence());
		
		assertEquals("Neurologue et psychiatre autrichien d’origine juive,"
				+ " Sigmund Freud est le père de la psychanalyse.", sentencesList.get(1).getSentence().trim());
		
		assertEquals("Ses théories ont souvent été controversées,"
				+ " mais Freud est sans aucun doute l’un des scientifiques qui aura le plus influencé la pensée de son siècle.",
				sentencesList.get(2).getSentence().trim());
	}

}
