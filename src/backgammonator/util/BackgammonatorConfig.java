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
	private static File propertiesFile = new File("backgammonator.properties");

	static {
		// load the properties
		if (propertiesFile.exists()) {
			// try to load the properties from the
			// file if found in the root directory
			try {
				properties.load(new FileInputStream(propertiesFile));
				System.out.println("[INFO] [Utils] "
						+ "Properties loaded successfully from file "
						+ propertiesFile.getAbsolutePath());
			} catch (FileNotFoundException ignore) {
				// cannot happen
			} catch (IOException e) {
				System.out.println("[ERROR] [Utils] "
						+ "Error while reading properties file:");
				e.printStackTrace();
			}

		} else {
			System.out.println("[WARNING] [Utils] "
					+ "Cannot find backgammonator.properties "
					+ "in the current directory");
			// try to load the properties if the file is not found in the
			// current
			// directory but maybe it can be placed in some library jar on the
			// class path -> in case of using the backgammonatorLibrary.jar as
			// demo or in the class path of a Java project
			URL properiesURL = BackgammonatorConfig.class
					.getResource("/backgammonator.properties");
			System.out.println("[INFO] [Utils] "
					+ "Properties loaded successfully from entry "
					+ properiesURL);
			if (properiesURL != null) {
				try {
					properties.load(properiesURL.openStream());
				} catch (IOException e) {
					System.out.println("[ERROR] [Utils] "
							+ "Error while reading properties file:");
					e.printStackTrace();
				}
			}
		}
	}

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
