package ma.ensao.pfa.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.AnalyzeSyntaxRequest;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import ma.ensao.pfa.util.Entity;
import ma.ensao.pfa.util.Paragraph;
import ma.ensao.pfa.util.Sentence;
import ma.ensao.pfa.util.Text;
import ma.ensao.pfa.util.Token;

public class GoogleCloudNL {
	/**
	 * Properties
	 */
	private static final Logger LOGGER = Logger.getLogger(GoogleCloudNL.class.getName());
	private static LanguageServiceClient language;
	public static final String ENTITIESFILENAME = "entityAnalysisResult.xml";
	public static final String TOKENSFILENAME = "syntaxAnalysisResult.xml";

	/**
	 * Setup the credentials and initialize the document builder to provides text
	 * analysis operations such as syntax analysis and entity analysis.
	 * 
	 * @param text to analyze
	 * @return Document object
	 * @throws IOException
	 */
	public Document init(String text) throws IOException {

		InputStream inputstream = new FileInputStream("PFA-NLP-CREDENTIALS.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(inputstream);

		LanguageServiceSettings languageServiceSettings = LanguageServiceSettings.newBuilder()
				.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
		LanguageServiceClient languageServiceClient = LanguageServiceClient.create(languageServiceSettings);

		Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
		language = languageServiceClient;
		return doc;
	}

	/**
	 * Provides named entities in the text along with entity types, salience.
	 * 
	 * @param text to analyze
	 * @return List of entities
	 * @throws IOException
	 */
	public List<Entity> entityAnalysis(String text) throws IOException {

		AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder().setDocument(init(text))
				.setEncodingType(EncodingType.UTF16).build();
		AnalyzeEntitiesResponse response = language.analyzeEntities(request);

		List<Entity> entitiesList = new ArrayList<>();
		LinkedHashSet<String> hashSet = new LinkedHashSet<>();

		for (com.google.cloud.language.v1.Entity entity : response.getEntitiesList()) {
			Entity ent = new Entity(entity.getName(), entity.getType().toString(), entity.getSalience());
			if (hashSet.add(entity.getName())) {
				entitiesList.add(ent);
			}

		}
		return entitiesList;
	}

	/**
	 * Analyzes the syntax of the text and provides tokenization along with part of
	 * speech tags, lemmatization, and other properties.
	 * 
	 * @param text to analyze
	 * @return List of tokens
	 * @throws IOException
	 */
	public List<Token> syntaxAnalysis(String text) throws IOException {
		AnalyzeSyntaxRequest request = AnalyzeSyntaxRequest.newBuilder().setDocument(init(text))
				.setEncodingType(EncodingType.UTF16).build();

		AnalyzeSyntaxResponse response = language.analyzeSyntax(request);
		List<Token> tokensList = new ArrayList<>();

		for (com.google.cloud.language.v1.Token token : response.getTokensList()) {

			Token t = new Token(token.getText().getContent(),
					checkIfUnknown(token.getPartOfSpeech().getGender().toString()), token.getLemma().toString(),
					checkIfUnknown(token.getPartOfSpeech().getNumber().toString()),
					checkIfUnknown(token.getPartOfSpeech().getTag().toString()),
					checkIfUnknown(token.getPartOfSpeech().getTense().toString()));

			tokensList.add(t);
		}
		return tokensList;
	}

	/**
	 * Create xml file that contains the result of entity analysis
	 * 
	 * @param tokensList
	 * @param text
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void printEntitiesXml(List<Entity> entitiesList) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("text");
			doc.appendChild(rootElement);

			for (Entity ent : entitiesList) {

				Element entity = doc.createElement("entity");
				entity.appendChild(doc.createTextNode(ent.getEntity()));
				rootElement.appendChild(entity);
				Attr attr = doc.createAttribute("type");
				attr.setValue(ent.getType());
				entity.setAttributeNode(attr);

				Attr attr2 = doc.createAttribute("salience");
				attr2.setValue(ent.getSalience() + "");
				entity.setAttributeNode(attr2);

			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(ENTITIESFILENAME);

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			LOGGER.log(Level.SEVERE, "ParserConfigurationException", pce);

		} catch (TransformerException tfe) {
			LOGGER.log(Level.SEVERE, "TransformerException", tfe);

		}

	}

	/**
	 * Create xml file that contains the result of syntax analysis
	 * 
	 * @param tokensList
	 * @param text
	 * @throws IOException
	 */
	public void printTokensXml(List<Token> tokensList, String text) throws IOException {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.newDocument();


			Element rootElement = doc.createElement("text");
			doc.appendChild(rootElement);

			Text txt = new Text(text);

			int paragraphId = 1;
			int sentenceId = 1;
			int k = 0, wordId = 1;
			for (Paragraph para : txt.paragraphesList()) {

				para.setId(paragraphId);
				Element parag = doc.createElement("paragraph");
				rootElement.appendChild(parag);
				Attr attr = doc.createAttribute("id");
				attr.setValue(para.getId() + "");
				parag.setAttributeNode(attr);

				for (Sentence sentence : para.sentencesList()) {
					sentence.setId(sentenceId);
					Element sen;
					sen = doc.createElement("sentence");
					parag.appendChild(sen);
					Attr sentenceIdAttr = doc.createAttribute("id");
					sentenceIdAttr.setValue(sentence.getId() + "");
					sen.setAttributeNode(sentenceIdAttr);
					for (Token word : sentence.tokensList()) {

						word.setId(wordId);
						wordId++;

						if (word.getToken().contains(tokensList.get(k).getToken())) {
							Element token = doc.createElement("token");
							token.appendChild(doc.createTextNode(tokensList.get(k).getToken()));
							sen.appendChild(token);

							Attr wordAttrId = doc.createAttribute("id");
							wordAttrId.setValue("" + word.getId());
							token.setAttributeNode(wordAttrId);

							Attr wordLemma = doc.createAttribute("lemma");
							wordLemma.setValue(tokensList.get(k).getLemma());
							token.setAttributeNode(wordLemma);

							Attr wordPos = doc.createAttribute("pos");
							wordPos.setValue(tokensList.get(k).getPos());
							token.setAttributeNode(wordPos);

							if (!tokensList.get(k).getGender().equals("")) {
								Attr wordGender = doc.createAttribute("gender");
								wordGender.setValue(tokensList.get(k).getGender() + "");
								token.setAttributeNode(wordGender);
							}

							if (!tokensList.get(k).getTense().equals("")) {

								Attr wordTense = doc.createAttribute("tense");
								wordTense.setValue(tokensList.get(k).getTense() + "");
								token.setAttributeNode(wordTense);

							}
							if (!tokensList.get(k).getNumber().equals("")) {

								Attr wordNumber = doc.createAttribute("number");
								wordNumber.setValue(tokensList.get(k).getNumber() + "");
								token.setAttributeNode(wordNumber);

							}

							k++;
						}

					}
					wordId = 1;
					sentenceId++;
				}
				sentenceId = 1;

				paragraphId++;

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(TOKENSFILENAME);
			transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			LOGGER.log(Level.SEVERE, "ParserConfigurationException", pce);
		} catch (TransformerException tfe) {
			LOGGER.log(Level.SEVERE, "TransformerException", tfe);

		}
	}

	/**
	 * Tests if the given token property is unknown.
	 * 
	 * @param String token
	 * @return Empty string if the property is unknown otherwise return the same
	 *         property string
	 */
	public String checkIfUnknown(String tokenProperty) {
		if (tokenProperty.contains("_UNKNOWN"))
			return "-";
		else
			return tokenProperty;
	}

}
