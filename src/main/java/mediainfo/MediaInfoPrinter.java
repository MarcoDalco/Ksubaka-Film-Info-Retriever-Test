package mediainfo;

import java.io.PrintStream;
import java.util.List;

import org.springframework.web.client.ResourceAccessException;

public class MediaInfoPrinter {
	private final PrintStream out;
	private final APIReader apiReader;

	public MediaInfoPrinter(APIReader apiReader, PrintStream out) {
		this.apiReader = apiReader;
		this.out = out;
	}

	public void queryAndPrintAbout(String filmName) {
		try {
			List<FilmInfo> filmInfos = apiReader.findFilms(filmName);
			for (FilmInfo filmInfo : filmInfos) {
				out.println("Title: " + filmInfo.getTitle() + ", year: " + filmInfo.getYear() + ", director: " + filmInfo.getDirectorName());
			}
		}
		catch (ResourceAccessException e) {
			out.println("Error trying to connect to the film database:");
			e.printStackTrace();
		}
	}

}
