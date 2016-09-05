package mediainfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mediainfo.imdb.IMDBAPIReader;
import mediainfo.tmdb.TMDBAPIReader;

public class ApiReaderFactory {
	private static final Map<String, APIReader> APIS = new HashMap<>();
	static {
		APIS.put("imdb", new IMDBAPIReader());
		APIS.put("tmdb", new TMDBAPIReader());
	}

	public static APIReader get(String websiteAPI) {
		return APIS.get(websiteAPI);
	}

	public static Set<String> getAPIOptions() {
		return APIS.keySet();
	}

}
