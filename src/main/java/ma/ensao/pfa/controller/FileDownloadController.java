package ma.ensao.pfa.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download")
public class FileDownloadController {
	private static final Logger LOGGER = Logger.getLogger(FileDownloadController.class.getName());

	/* methode HTTP-GET qui s'occupe de fournir le lien de téléchargement du fichier XML. */
	@GetMapping("/file/{fileName:.+}")
	public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		InputStream inputStream;
		String sourceURI = request.getHeader("referer") == null ? "" : request.getHeader("referer");

		if (sourceURI.endsWith("entityResult") || sourceURI.endsWith("syntaxResult")) {
			File file = new File(fileName);
			if (file.exists()) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {

					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);
				response.setHeader("Content-Disposition",
						String.format("attachment; filename=\"" + file.getName() + "\""));

				response.setContentLength((int) file.length());

				inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {

					LOGGER.log(Level.WARNING, "IOException", e);
				} finally {
					inputStream.close();

				}
			} else {
				throw new FileNotFoundException("Fichier introuvable.");

			}

		} else {
			throw new FileNotFoundException("Fichier introuvable.");

		}
	}
}
