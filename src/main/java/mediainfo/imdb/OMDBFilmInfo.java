package mediainfo.imdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OMDBFilmInfo {

	public OMDBFilmInfo() {
		super();
	}

	public OMDBFilmInfo(String title, String year, String directorName) {
		super();
		this.title = title;
		this.year = year;
		this.directorName = directorName;
	}

	@JsonProperty("Title")
	private String title;
	@JsonProperty("Year")
	private String year;
	@JsonProperty("Director")
	private String directorName;

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getDirectorName() {
		return directorName;
	}

}
