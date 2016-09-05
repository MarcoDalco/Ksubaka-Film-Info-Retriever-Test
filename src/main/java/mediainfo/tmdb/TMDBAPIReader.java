package mediainfo.tmdb;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mediainfo.APIReader;
import mediainfo.FilmInfo;

public class TMDBAPIReader implements APIReader {

	private static final String API_KEY = "702181b2811bdf2a5ecd48acc833daf5";
	private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";

	@Override
	public List<FilmInfo> findFilms(String filmTitle) {
    RestTemplate restTemplate = new RestTemplate();
    List<TMDBFilmInfoSearchEntry> searchResults = searchEntriesForFilmIDs(filmTitle, restTemplate);

    return getFilmInfos(restTemplate, searchResults);
	}

	private List<FilmInfo> getFilmInfos(RestTemplate restTemplate, List<TMDBFilmInfoSearchEntry> searchResults) {
		List<FilmInfo> filmInfos = new ArrayList<>();
		for (TMDBFilmInfoSearchEntry searchResult : searchResults) {
	    URI filmInfoByIDURI = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
					.pathSegment("movie/" + searchResult.getID() + "/credits")
	    		.queryParam("api_key", API_KEY)
	    		.build().toUri();
			TMDBFilmCreditInfoSearch creditInfoSearch = restTemplate.getForObject(filmInfoByIDURI, TMDBFilmCreditInfoSearch.class);
			filmInfos.add(new FilmInfo(searchResult.getTitle(), searchResult.getYear(), findDirector(creditInfoSearch.getCrew())));
		}
		return filmInfos;
	}

	private String findDirector(List<TMDBFilmCreditInfo> cast) {
		if (cast != null) {
			for (TMDBFilmCreditInfo info : cast) {
				if ("director".equalsIgnoreCase(info.getJob())) {
					return info.getName();
				}
			}
		}
		return "unknown";
	}

	private List<TMDBFilmInfoSearchEntry> searchEntriesForFilmIDs(String filmTitle, RestTemplate restTemplate) {
		URI uri = UriComponentsBuilder.fromHttpUrl(TMDB_API_BASE_URL)
				.pathSegment("search/movie")
    		.queryParam("api_key", API_KEY)
    		.queryParam("query", filmTitle).build().toUri();
		TMDBFilmInfoSearch tmdbFilmInfoSearch = restTemplate.getForObject(uri, TMDBFilmInfoSearch.class);
		return tmdbFilmInfoSearch.getFilmInfoList();
	}

}
