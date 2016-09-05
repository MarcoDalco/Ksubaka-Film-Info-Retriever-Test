package mediainfo;

public class FilmInfo {

	private String title;
	private String year;
	private String directorName;

	public FilmInfo(String title, String year, String directorName) {
		super();
		this.title = title;
		this.year = year;
		this.directorName = directorName;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getDirectorName() {
		return directorName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directorName == null) ? 0 : directorName.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilmInfo other = (FilmInfo) obj;
		if (directorName == null) {
			if (other.directorName != null)
				return false;
		} else if (!directorName.equals(other.directorName))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FilmInfo [title=" + title + ", year=" + year + ", directorName=" + directorName + "]";
	}

}
