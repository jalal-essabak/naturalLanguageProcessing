package ma.ensao.pfa.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

public class File {
	private static final Logger LOGGER = Logger.getLogger(File.class.getName());

	/**
	 * Return the contents of a text file as a String
	 * 
	 * @param filePath The document path to get the text from.
	 * @return String The text of the text document.
	 * @throws IOException
	 */
	public static String readFile(String filePath) throws IOException {
		String text = "";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {

			int intch;
			while ((intch = br.read()) != -1) {
				text += (char) intch;
			}
		} finally {
			br.close();
		}
		return text;
	}

	/**
	 * Write a given String into a txt file
	 * 
	 * @param text
	 */
	public static void writeFile(String text) throws IOException {
		Writer out = null;
		try {
			out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("toTranslate.txt"), StandardCharsets.UTF_8));
		} catch (FileNotFoundException e1) {
			LOGGER.log(Level.WARNING, "FileNotFoundException", e1);
			;
		}
		try {
			if (out != null)
				out.write(text);
			else
				System.out.println();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "IOException", e);
			;
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "IOException", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Return the contents of the PDF file bytes as a String
	 * 
	 * @param PdfFileBytes The PDF bytes to get the text from.
	 * @return The text of the PDF document.
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static String convertPDFToTxt(byte[] PdfFileBytes) throws InvalidPasswordException, IOException {

		PDDocument pddDoc = PDDocument.load(PdfFileBytes);
		PDFTextStripper reader = new PDFTextStripper();
		String pdfText = reader.getText(pddDoc);
		pddDoc.close();
		return pdfText;

	}
	
	/**
	 * 
	 * @param document  file uploaded by the user
	 * @param inputText value of the text area
	 * @return String that represent the text to analyze
	 * @throws InvalidPasswordException
	 * @throws IOException
	 */
	public static String inputText(MultipartFile document, String inputText) throws InvalidPasswordException, IOException {
		String text = "";

		if (document.isEmpty()) {
			text = inputText;
		} else {
			if (document.getOriginalFilename().endsWith(".pdf"))
				text = File.convertPDFToTxt(document.getBytes());
			else
				text = new String(document.getBytes());

		}

		return text;
	}

}
