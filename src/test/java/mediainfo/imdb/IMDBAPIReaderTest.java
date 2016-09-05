package mediainfo.imdb;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestTemplate;

import mediainfo.FilmInfo;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class IMDBAPIReaderTest {
	@Mocked RestTemplate restTemplate;

	@SuppressWarnings("unused")
	@Before
	public void setUp() throws Exception {
		new StrictExpectations() {{
			new RestTemplate(); result = restTemplate; times = 1;
		}};
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testNoResults() {
		// Expect search REST call
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?s=Indiana%20Jones&type=movie&r=json"),
				new OMDBFilmInfoSearch(new ArrayList<>()));

		// Act
		IMDBAPIReader cut = new IMDBAPIReader();
		List<FilmInfo> filmInfos = cut.findFilms("Indiana Jones");

		// Assert
		Assert.assertEquals(0, filmInfos.size());
	}

	@SuppressWarnings("unused")
	private void expectRESTCall(URI expectedUri, Object searchResult) {
		new StrictExpectations() {{
			restTemplate.getForObject(expectedUri, searchResult.getClass()); result = searchResult; times = 1;
		}};
	}

	@Test
	public void testOneResult() {
		// Expect search REST call
		List<OMDBFilmInfoSearchEntry> filmInfoList = Collections.singletonList(new OMDBFilmInfoSearchEntry("Title1", "ID1"));
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?s=Indiana%20Jones&type=movie&r=json"),
				new OMDBFilmInfoSearch(filmInfoList));

		// Expect "get details" REST call
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?i=ID1&r=json"),
				new OMDBFilmInfo("Title 1", "1999", "Director 1"));
		
		// Act
		IMDBAPIReader cut = new IMDBAPIReader();
		List<FilmInfo> actualFilmInfos = cut.findFilms("Indiana Jones");

		// Assert
		List<FilmInfo> expectedFilmInfos = Collections.singletonList(new FilmInfo("Title 1", "1999", "Director 1"));
		Assert.assertEquals(expectedFilmInfos, actualFilmInfos);
	}

	@Test
	public void testTwoResults() {
		// Expect search REST call
		List<OMDBFilmInfoSearchEntry> filmInfoList = new ArrayList<>();
		filmInfoList.add(new OMDBFilmInfoSearchEntry("Title1", "ID1"));
		filmInfoList.add(new OMDBFilmInfoSearchEntry("Title2", "ID2"));
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?s=Indiana%20Jones&type=movie&r=json"),
				new OMDBFilmInfoSearch(filmInfoList));

		// Expect "get details" REST call 1
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?i=ID1&r=json"),
				new OMDBFilmInfo("Title 1", "1999", "Director 1"));

		// Expect "get details" REST call 2
		expectRESTCall(
				URI.create("http://www.omdbapi.com/?i=ID2&r=json"),
				new OMDBFilmInfo("Title 2", "2000", "Director 2"));
		
		// Act
		IMDBAPIReader cut = new IMDBAPIReader();
		List<FilmInfo> filmInfos = cut.findFilms("Indiana Jones");

		// Assert
		List<FilmInfo> expectedFilmInfos = new ArrayList<>();
		expectedFilmInfos.add(new FilmInfo("Title 1", "1999", "Director 1"));
		expectedFilmInfos.add(new FilmInfo("Title 2", "2000", "Director 2"));
		Assert.assertEquals(expectedFilmInfos, filmInfos);
	}

}
