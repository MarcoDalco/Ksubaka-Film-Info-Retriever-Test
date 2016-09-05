package mediainfo.tmdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBFilmInfoSearchEntry {

	public TMDBFilmInfoSearchEntry() {
		super();
	}

	public TMDBFilmInfoSearchEntry(String title, String id, String releaseDate) {
		super();
		this.title = title;
		this.id = id;
		this.releaseDate = releaseDate;
	}

	private static final SimpleDateFormat RELEASE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private String title;
	private String id;
	@JsonProperty("release_date")
	private String releaseDate;

	public String getTitle() {
		return title;
	}

	public String getID() {
		return id;
	}

	public String getYear() {
		if (releaseDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(RELEASE_DATE_FORMAT.parse(releaseDate));
			return "" + calendar.get(Calendar.YEAR);
		}
		catch (ParseException e) {
			// Silently failing. Not correct, but best effort.
			return "Invalid date";
		}
	}

}
