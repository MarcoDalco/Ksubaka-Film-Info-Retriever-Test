package mediainfo.imdb;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mediainfo.APIReader;
import mediainfo.FilmInfo;

public class IMDBAPIReader implements APIReader {

	private static final String OMDB_API_BASE_URL = "http://www.omdbapi.com/";

	@Override
	public List<FilmInfo> findFilms(String filmTitle) {
    RestTemplate restTemplate = new RestTemplate();
    List<OMDBFilmInfoSearchEntry> searchResults = searchEntriesForFilmIDs(filmTitle, restTemplate);

    List<OMDBFilmInfo> omdbFilmInfos = getFilmInfos(restTemplate, searchResults);

		return omdbFilmInfos.stream().map((OMDBFilmInfo filmInfo) -> toFilmInfo(filmInfo)).collect(Collectors.toList());
	}

	private FilmInfo toFilmInfo(OMDBFilmInfo filmInfo) {
		return new FilmInfo(filmInfo.getTitle(), filmInfo.getYear(), filmInfo.getDirectorName());
	}

	private List<OMDBFilmInfo> getFilmInfos(RestTemplate restTemplate, List<OMDBFilmInfoSearchEntry> searchResults) {
		List<OMDBFilmInfo> filmInfos = new ArrayList<>();
		for (OMDBFilmInfoSearchEntry searchResult : searchResults) {
	    URI filmInfoByIDURI = UriComponentsBuilder.fromHttpUrl(OMDB_API_BASE_URL)
	    		.queryParam("i", searchResult.getIMDBID())
	    		.queryParam("r", "json").build().toUri();
			filmInfos.add(restTemplate.getForObject(filmInfoByIDURI, OMDBFilmInfo.class));
		}
		return filmInfos;
	}

	private List<OMDBFilmInfoSearchEntry> searchEntriesForFilmIDs(String filmTitle, RestTemplate restTemplate) {
		URI uri = UriComponentsBuilder.fromHttpUrl(OMDB_API_BASE_URL)
    		.queryParam("s", filmTitle)
    		.queryParam("type", "movie")
    		.queryParam("r", "json").build().toUri();
		OMDBFilmInfoSearch omdbFilmInfoSearch = restTemplate.getForObject(uri, OMDBFilmInfoSearch.class);
		return omdbFilmInfoSearch.getFilmInfoList();
	}

}
