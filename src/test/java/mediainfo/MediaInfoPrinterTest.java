package mediainfo;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class MediaInfoPrinterTest {
	@Mocked APIReader apiReader;
	@Mocked PrintStream out;
	MediaInfoPrinter cut;

	@Before
	public void setUp() throws Exception {
		cut = new MediaInfoPrinter(apiReader, out);
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testNoResults() {
		@SuppressWarnings("unused")
		StrictExpectations strictExpectations = new StrictExpectations() {{
			apiReader.findFilms("Title"); result = Collections.emptyList(); times = 1;
		}};

		cut.queryAndPrintAbout("Title");
	}

	@Test
	public void testOneResult() {
		FilmInfo filmInfo = new FilmInfo("Title 1", "1999", "Director 1");
		List<FilmInfo> filmInfos = Collections.singletonList(filmInfo);

		@SuppressWarnings("unused")
		StrictExpectations strictExpectations = new StrictExpectations() {{
			apiReader.findFilms("Title"); result = filmInfos; times = 1;
			out.println("Title: Title 1, year: 1999, director: Director 1");
		}};

		cut.queryAndPrintAbout("Title");
	}

	@Test
	public void testTwoResults() {
		List<FilmInfo> filmInfos = new ArrayList<>();
		filmInfos.add(new FilmInfo("Title 1", "1999", "Director 1"));
		filmInfos.add(new FilmInfo("Title 2", "2000", "Director 2"));

		@SuppressWarnings("unused")
		StrictExpectations strictExpectations = new StrictExpectations() {{
			apiReader.findFilms("Title"); result = filmInfos; times = 1;
			out.println("Title: Title 1, year: 1999, director: Director 1");
			out.println("Title: Title 2, year: 2000, director: Director 2");
		}};

		cut.queryAndPrintAbout("Title");
	}

}
