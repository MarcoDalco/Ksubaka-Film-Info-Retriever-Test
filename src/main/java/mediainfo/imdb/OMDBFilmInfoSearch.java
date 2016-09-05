package mediainfo.imdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBFilmInfoSearch {
	public OMDBFilmInfoSearch() {
		super();
	}

	public OMDBFilmInfoSearch(List<OMDBFilmInfoSearchEntry> search) {
		super();
		this.search = search;
	}

	@JsonProperty("Search")
	private List<OMDBFilmInfoSearchEntry> search;

	@JsonIgnore
	public List<OMDBFilmInfoSearchEntry> getFilmInfoList() {
		return search;
	}
}
