package mediainfo.tmdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBFilmInfoSearch {
	public TMDBFilmInfoSearch() {
		super();
	}

	public TMDBFilmInfoSearch(List<TMDBFilmInfoSearchEntry> results) {
		super();
		this.results = results;
	}

	@JsonProperty("results")
	private List<TMDBFilmInfoSearchEntry> results;

	@JsonIgnore
	public List<TMDBFilmInfoSearchEntry> getFilmInfoList() {
		return results;
	}
}
