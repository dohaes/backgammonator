package backgammonator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Holds configuration properties for the system.
 */
public final class BackgammonatorConfig {

	private static Properties properties = new Properties();
	private static boolean propertiesInited = false;
	private static boolean propertiesFileNotFound = false;
	private static File propertiesFile = new File("backgammonator.properties");

	/**
	 * Gets the system property associated with the given key.
	 * 
	 * @param key the key
	 * @param alternative the alternative value to be returned if not found. Can
	 *            be <code>null</code>.
	 * @return the value of the property or <code>alternative</code> if there is
	 *         no value associated with the given key.
	 */
	public static String getProperty(String key, String alternative) {
		if (!propertiesInited && !propertiesFileNotFound) {
			if (!propertiesFile.exists()) {
				propertiesFileNotFound = true;
				Debug.getInstance().warning("Properties file not found",
						Debug.UTILS, null);
				// try to find the file as resource from some jar on the class
				// path
				URL properies = BackgammonatorConfig.class
						.getResource("/backgammonator.properties");
				System.out.println("========== properies : " + properies);
				if (properies != null) try {
					properties.load(properies.openStream());
				} catch (IOException e) {
					Debug.getInstance().warning(
							"Error while reading properties file", Debug.UTILS,
							e);
				}
				propertiesInited = true;
				return alternative;
			}
		} else {// properties file exists
			try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (FileNotFoundException e) {
				Debug.getInstance().warning("Properties file not found",
						Debug.UTILS, e);
			} catch (IOException e) {
				Debug.getInstance().warning(
						"Error while reading properties file", Debug.UTILS, e);
			}
			propertiesInited = true;
		}
		return properties.size() == 0 ? alternative : properties
				.getProperty(key);
	}

	/**
	 * Gets the system property associated with the given key.
	 * 
	 * @param key the key
	 * @return the value of the property or <code>null</code> if there is no
	 *         value associated with the given key.
	 */
	public static String getProperty(String key) {
		return getProperty(key, null);
	}

	/**
	 * @see BackgammonatorConfig#getProperty(String, String)
	 * @return the value of the property parsed to <code>int</code>
	 */
	public static int getProperty(String key, int alternative) {
		String value = getProperty(key);
		return value == null ? alternative : Integer.parseInt(value);
	}

	/**
	 * @see BackgammonatorConfig#getProperty(String, String)
	 * @return the value of the property parsed to <code>long</code>
	 */
	public static long getProperty(String key, long alternative) {
		String value = getProperty(key);
		return value == null ? alternative : Long.parseLong(value);
	}

	/**
	 * @see BackgammonatorConfig#getProperty(String, String)
	 * @return the value of the property parsed to <code>boolean</code>
	 */
	public static boolean getProperty(String key, boolean alternative) {
		String value = getProperty(key);
		return value == null ? alternative : Boolean.parseBoolean(value);
	}

}
