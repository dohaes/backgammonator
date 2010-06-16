package backgammonator.test.config;

import backgammonator.util.BackgammonatorConfig;
import junit.framework.TestCase;

/**
 * Tests configurating the system vie the backgammonator.properties file.
 */
public class ConfigurationTestCase extends TestCase {

	/**
	 * Tests loading of the properties.
	 */
	public void testConfigurationLoad() {
		//test debug configuration
		assertEquals(false, BackgammonatorConfig.getProperty("backgammonator.debug.enabled", true));
		assertEquals(false, BackgammonatorConfig.getProperty("backgammonator.debug.onConsole", true));
		assertEquals(false, BackgammonatorConfig.getProperty("backgammonator.debug.inFile", true));
		
		//test game configuration
        assertEquals(4000, BackgammonatorConfig.getProperty("backgammonator.game.moveTimeout", 1000));
		assertEquals(20, BackgammonatorConfig.getProperty("backgammonator.game.maxProcesses", 30));
		assertEquals(1024, BackgammonatorConfig.getProperty("backgammonator.game.maxMemmoryUsage", 200));
		assertEquals("reports_test", BackgammonatorConfig.getProperty("backgammonator.logger.outputDir"));
		
		//test tournament configuration
		assertEquals("special", BackgammonatorConfig.getProperty("backgammonator.tournament.rate"));
		assertEquals("reports_test", BackgammonatorConfig.getProperty("backgammonator.logger.outputDir"));
		
		//test web configuration
		assertEquals("uploads_test", BackgammonatorConfig.getProperty("backgammonator.web.uploadDir"));
		assertEquals("repository_test", BackgammonatorConfig.getProperty("backgammonator.web.repositoryDir"));
		assertEquals(1024, BackgammonatorConfig.getProperty("backgammonator.web.uploadMaxSize", 200));
	}

}
