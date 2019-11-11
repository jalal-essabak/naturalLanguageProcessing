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
import ma.ensao.pfa.util.Text;

public class TextTest {
	List<Paragraph> paragraphesList;
	Text text;
	@Before
	public void setUp() throws Exception {
		paragraphesList = new ArrayList<Paragraph>();
		text=new Text();
	}

	@After
	public void tearDown() throws Exception {
	paragraphesList=null;
	text=null;
	}

	@Test
	public void testParagraphesList() throws IOException {
		
		String txt=File.readFile("test-files/TextTest.txt"); 
		text.setText(txt);
		paragraphesList=text.paragraphesList();
		
		//premiere Paragraphe;
		assertEquals("Paris est la capitale de la France. L’agglomération de Paris compte plus de 10 millions d’habitants."
				+ " Un fleuve traverse la capitale française, c’est la Seine. Dans Paris, il y a deux îles :"
				+ "  l’île de la Cité et l’île Saint-Louis.",paragraphesList.get(0).getParagraph());
		
		//deuxieme Paragraphe;
		assertEquals("Paris compte vingt arrondissements. Le 16e, le 7e et le 8e arrondissements de Paris sont les quartiers les plus riches."
				+ " Ils sont situés dans l’ouest de la capitale. Les quartiers populaires comme le 19e et le 20e sont au nord-est de la ville. Les monuments ; "
				+ "célèbres, les ministères, le palais de l’Élysée sont situés dans le centre de Paris."
				,paragraphesList.get(1).getParagraph());
		
		//troisieme Paragraphe;
		assertEquals("Paris est une ville très touristique. Chaque année, des millions de touristes du monde entier marchent sur les Champs-Élysées."
				+ " Ils séjournent à l’hôtel, louent des chambres d’hôtes ou des appartements pour une semaine." 
				,paragraphesList.get(2).getParagraph());
	}

}
