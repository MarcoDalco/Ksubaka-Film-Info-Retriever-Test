package mediainfo.tmdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBFilmCreditInfoSearch {

	public TMDBFilmCreditInfoSearch() {
		super();
	}

	public TMDBFilmCreditInfoSearch(List<TMDBFilmCreditInfo> crew) {
		super();
		this.crew = crew;
	}

	private List<TMDBFilmCreditInfo> crew;

	public List<TMDBFilmCreditInfo> getCrew() {
		return crew;
	}

}
