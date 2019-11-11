package ma.ensao.pfa.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ma.ensao.pfa.nlp.GoogleCloudNL;
import ma.ensao.pfa.util.Entity;
import ma.ensao.pfa.util.File;
import ma.ensao.pfa.util.Token;

/* Le contrôleur reliant entre l'interface utilisateur (client), et le moteur NLP. */
@Controller
public class MainController {

	/* Les methodes de routage spring - contrôlés par des méthodes HTTP GET */
	// routage vers la page d'acceuil
	@GetMapping(value = "/")
	public String index() {
		return "home";
	}

	// routage vers la page d'analyse syntaxique
	@GetMapping(value = "/syntax")
	public String syntax() {
		return "syntax";
	}
	
	// routage vers la page d'analyse d'entité
	@GetMapping(value = "/entity")
	public String entity() {
		return "entity";
	}

	// routage vers la page de traduction
	@GetMapping(value = "/translate")
	public String translate() {
		return "translate";
	}

	/* Les methodes envoyant les données rentré par l'user dans les formulaires. - contrôlés par des méthodes HTTP POST */
	// HTTP-POST concernant l'analyse syntaxique
	@PostMapping(value = "syntaxResult", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String syntaxResult(@RequestParam("text") String inputField, @RequestParam("file") MultipartFile document,
			Model model, HttpServletRequest request) throws IOException {

		GoogleCloudNL nl = new GoogleCloudNL();
		String text = "";
		text = File.inputText(document, inputField);

		List<Token> tokensList = nl.syntaxAnalysis(text);

		nl.printTokensXml(tokensList, text);

		model.addAttribute("fileName", GoogleCloudNL.TOKENSFILENAME);
		model.addAttribute("tokensList", tokensList);

		return "syntax-analysis-result";
	}

	// HTTP-POST concernant l'analyse des entités
	@PostMapping(value = "entityResult", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String entityResult(@RequestParam("text") String inputField, @RequestParam("file") MultipartFile document,
			Model model, HttpServletRequest request) throws IOException {

		GoogleCloudNL nl = new GoogleCloudNL();
		String text = "";
		text = File.inputText(document, inputField);

		List<Entity> entitiesList = nl.entityAnalysis(text);

		nl.printEntitiesXml(entitiesList);

		model.addAttribute("fileName", GoogleCloudNL.ENTITIESFILENAME);
		model.addAttribute("entitiesList", entitiesList);

		return "entity-analysis-result";
	}

	// HTTP-POST concernant la traduction
	@PostMapping(value = "translateResult", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String translateResult(@RequestParam("text") String inputField, @RequestParam("file") MultipartFile document,
			Model model, HttpServletRequest request) throws IOException, InterruptedException {

		String text = "";
		text = File.inputText(document, inputField);

		File.writeFile(text.replaceAll("(?m)^[ \t]*\r?\n", ""));
		new java.io.File("translateResult.txt").delete();
		
		// Linux. Si vous utilisez linux, veuillez décommenter cette ligne
		// Runtime.getRuntime().exec("python src/main/python/translate.py -model src/main/python/demo-model_100000.pt -src toTranslate.txt -output translateResult.txt", null);
		
		// Windows. Sinon, si votre système est Windows, veuillez décommenter cette ligne
		Runtime.getRuntime().exec("python src\\main\\python\\translate.py -model src\\main\\python\\demo-model_100000.pt -src toTranslate.txt -output translateResult.txt", null);

		while (true) {
			if (new java.io.File("translateResult.txt").exists()) {
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.interrupted();
				e.printStackTrace();
			}
		}

		String translation = File.readFile("translateResult.txt");

		model.addAttribute("originalText", text);
		model.addAttribute("translateResult", translation);

		return "translation-result";
	}

}
