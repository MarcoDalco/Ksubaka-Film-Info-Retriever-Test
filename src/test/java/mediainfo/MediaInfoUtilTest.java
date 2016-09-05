package mediainfo;

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
	@Mocked ApiReaderFactory factory;
	@Mocked MediaInfoPrinter printer;

	@Before
	public void setUp() throws Exception {
		Properties systemProperties = System.getProperties();
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
		new StrictExpectations() {{
			ApiReaderFactory.get(null); result = null; times = 1;
			ApiReaderFactory.getAPIOptions(); result = optionsSet; times = 1;
			new MediaInfoPrinter(null, System.out); result = null; times = 0;
		}};

		MediaInfoUtil.main(new String []{});
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
