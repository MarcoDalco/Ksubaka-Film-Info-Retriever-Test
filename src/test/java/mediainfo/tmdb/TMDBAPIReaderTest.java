package mediainfo.tmdb;

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
public class TMDBAPIReaderTest {
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
				URI.create("https://api.themoviedb.org/3/search/movie?api_key=702181b2811bdf2a5ecd48acc833daf5&query=Indiana%20Jones"),
				new TMDBFilmInfoSearch(new ArrayList<>()));

		// Act
		TMDBAPIReader cut = new TMDBAPIReader();
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
	public void testOneResultNoDirector() {
		// Expect search REST call
		List<TMDBFilmInfoSearchEntry> filmInfoList = Collections.singletonList(new TMDBFilmInfoSearchEntry("Title 1", "ID1", "1999-08-01"));
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/search/movie?api_key=702181b2811bdf2a5ecd48acc833daf5&query=Indiana%20Jones"),
				new TMDBFilmInfoSearch(filmInfoList));

		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/movie/ID1/credits?api_key=702181b2811bdf2a5ecd48acc833daf5"),
				new TMDBFilmCreditInfoSearch(new ArrayList<>()));
		
		// Act
		TMDBAPIReader cut = new TMDBAPIReader();
		List<FilmInfo> actualFilmInfos = cut.findFilms("Indiana Jones");

		// Assert
		List<FilmInfo> expectedFilmInfos = Collections.singletonList(new FilmInfo("Title 1", "1999", "unknown"));
		Assert.assertEquals(expectedFilmInfos, actualFilmInfos);
	}

	@Test
	public void testOneResultWithDirector() {
		// Expect search REST call
		List<TMDBFilmInfoSearchEntry> filmInfoList = Collections.singletonList(new TMDBFilmInfoSearchEntry("Title 1", "ID1", "1999-12-13"));
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/search/movie?api_key=702181b2811bdf2a5ecd48acc833daf5&query=Indiana%20Jones"),
				new TMDBFilmInfoSearch(filmInfoList));

		ArrayList<TMDBFilmCreditInfo> crew = new ArrayList<>();
		crew.add(new TMDBFilmCreditInfo("Director of Photography", "Director of Photo."));
		crew.add(new TMDBFilmCreditInfo("Director", "Steven Spielberg"));
		crew.add(new TMDBFilmCreditInfo("Director of something else", "Director of Other stuff."));

		// Expect "get details" REST call
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/movie/ID1/credits?api_key=702181b2811bdf2a5ecd48acc833daf5"),
				new TMDBFilmCreditInfoSearch(crew));
		
		// Act
		TMDBAPIReader cut = new TMDBAPIReader();
		List<FilmInfo> actualFilmInfos = cut.findFilms("Indiana Jones");

		// Assert
		List<FilmInfo> expectedFilmInfos = Collections.singletonList(new FilmInfo("Title 1", "1999", "Steven Spielberg"));
		Assert.assertEquals(expectedFilmInfos, actualFilmInfos);
	}

	@Test
	public void testTwoResults() {
		// Expect search REST call
		List<TMDBFilmInfoSearchEntry> filmInfoList = new ArrayList<>();
		filmInfoList.add(new TMDBFilmInfoSearchEntry("Title 1", "ID1", "1999-09-02"));
		filmInfoList.add(new TMDBFilmInfoSearchEntry("Title 2", "ID2", "Release date 2"));
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/search/movie?api_key=702181b2811bdf2a5ecd48acc833daf5&query=Indiana%20Jones"),
				new TMDBFilmInfoSearch(filmInfoList));

		// Expect "get details" REST call 1
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/movie/ID1/credits?api_key=702181b2811bdf2a5ecd48acc833daf5"),
				new TMDBFilmCreditInfoSearch(new ArrayList<>()));

		ArrayList<TMDBFilmCreditInfo> crew = new ArrayList<>();
		crew.add(new TMDBFilmCreditInfo("Director of Photography", "Director of Photo."));
		crew.add(new TMDBFilmCreditInfo("Director", "Steven Spielberg"));
		crew.add(new TMDBFilmCreditInfo("Director of something else", "Director of Other stuff."));

		// Expect "get details" REST call 2
		expectRESTCall(
				URI.create("https://api.themoviedb.org/3/movie/ID2/credits?api_key=702181b2811bdf2a5ecd48acc833daf5"),
				new TMDBFilmCreditInfoSearch(crew));
		
		// Act
		TMDBAPIReader cut = new TMDBAPIReader();
		List<FilmInfo> filmInfos = cut.findFilms("Indiana Jones");

		// Assert
		List<FilmInfo> expectedFilmInfos = new ArrayList<>();
		expectedFilmInfos.add(new FilmInfo("Title 1", "1999", "unknown"));
		expectedFilmInfos.add(new FilmInfo("Title 2", "Invalid date", "Steven Spielberg"));
		Assert.assertEquals(expectedFilmInfos, filmInfos);
	}

}
