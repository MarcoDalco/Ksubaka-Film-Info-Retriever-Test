package mediainfo;

import java.util.List;

public interface APIReader {

	List<FilmInfo> findFilms(String filmName);

}
