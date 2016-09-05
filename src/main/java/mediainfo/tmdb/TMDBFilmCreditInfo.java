package mediainfo.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBFilmCreditInfo {

	public TMDBFilmCreditInfo() {
		super();
	}

	public TMDBFilmCreditInfo(String job, String name) {
		super();
		this.job = job;
		this.name = name;
	}

	@JsonProperty("job")
	private String job;
	private String name;

	public String getJob() {
		return job;
	}

	public String getName() {
		return name;
	}

}
