package mediainfo.imdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBFilmInfoSearchEntry {

	public OMDBFilmInfoSearchEntry() {
		super();
	}

	public OMDBFilmInfoSearchEntry(String title, String imdbID) {
		super();
		this.title = title;
		this.imdbID = imdbID;
	}

	@JsonProperty("Title")
	private String title;
	@JsonProperty("imdbID")
	private String imdbID;

	public String getTitle() {
		return title;
	}

	public String getIMDBID() {
		return imdbID;
	}

}
