package mediainfo;

public class MediaInfoUtil {
	public static void main(String[] args) {
		final String websiteAPI = System.getProperty("api");
		final String filmTitle = System.getProperty("movie");

		APIReader apiReader = ApiReaderFactory.get(websiteAPI);
		if ((apiReader == null)
			|| (filmTitle == null) || (filmTitle.length() == 0)) {
			printUsage();
		} else {
			new MediaInfoPrinter(apiReader, System.out).queryAndPrintAbout(filmTitle);
		}
	}

	private static void printUsage() {
		String apiOptions = String.join("|", ApiReaderFactory.getAPIOptions());
		System.out.println("Usage: MediaInfoUtil -Dapi=(" + apiOptions + ") -Dmovie=<film title>");
	}

}
