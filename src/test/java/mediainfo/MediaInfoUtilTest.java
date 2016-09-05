package mediainfo;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class MediaInfoUtilTest {
	@Mocked ApiReaderFactory apiReaderFactory;
	@Mocked APIReader imdbAPIReader;
	PrintStream out = System.out;
	@Mocked MediaInfoPrinter mediaInfoPrinter;
	Properties systemProperties = System.getProperties();

	@Before
	public void setUp() throws Exception {
		systemProperties.remove("api");
		systemProperties.remove("movie");
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testUsageIfNoPropertiesSpecified() {
		HashSet<String> optionsSet = new HashSet<>();
		optionsSet.add("option1");
		optionsSet.add("option2");
		@SuppressWarnings("unused")
		StrictExpectations strictExpectations = new StrictExpectations() {{
			ApiReaderFactory.get(null); result = null; times = 1;
			ApiReaderFactory.getAPIOptions(); result = optionsSet; times = 1;
			new MediaInfoPrinter(null, out); result = null; times = 0;
		}};

		MediaInfoUtil.main(new String []{});
	}

	@Test
	public void testUsageIfPropertiesAreOK() {
		HashSet<String> optionsSet = new HashSet<>();
		optionsSet.add("option1");
		optionsSet.add("option2");
		@SuppressWarnings("unused")
		StrictExpectations strictExpectations = new StrictExpectations() {{
			ApiReaderFactory.get("imdb"); result = imdbAPIReader; times = 1;
			ApiReaderFactory.getAPIOptions(); result = optionsSet; times = 0;
			new MediaInfoPrinter(imdbAPIReader, out); result = mediaInfoPrinter; times = 1;
			mediaInfoPrinter.queryAndPrintAbout("Indiana Jones"); times = 1;
		}};

		systemProperties.put("api", "imdb");
		systemProperties.put("movie", "Indiana Jones");

		MediaInfoUtil.main(new String []{});
	}

}
