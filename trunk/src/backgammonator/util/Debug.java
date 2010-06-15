package backgammonator.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Used for logging program behavior (warnings, errors, info) during execution.
 * The debug entry must contain level, message, Throwable object and module ID.
 */
public final class Debug {

	/**
	 * Indicates that the debug message is logged from the protocol module.
	 */
	public static final int PROTOCOL = 1;

	/**
	 * Indicates that the debug message is logged from the game logic module.
	 */
	public static final int GAME_LOGIC = 2;

	/**
	 * Indicates that the debug message is logged from the game logger module.
	 */
	public static final int GAME_LOGGER = 4;

	/**
	 * Indicates that the debug message is logged from the tournament logic
	 * module.
	 */
	public static final int TOURNAMENT_LOGIC = 5;

	/**
	 * Indicates that the debug message is logged from the tournament logger
	 * module.
	 */
	public static final int TOURNAMENT_LOGGER = 6;

	/**
	 * Indicates that the debug message is logged from the utils module.
	 */
	public static final int UTILS = 7;

	/**
	 * Indicates that the debug message is logged from the database module.
	 */
	public static final int DATABASE = 8;

	/**
	 * Indicates that the debug message is logged from the web interface module.
	 */
	public static final int WEB_INTERFACE = 9;

	private static boolean debugOn = BackgammonatorConfig.getProperty(
			"backgammonator.debug.enabled", true);
	private static boolean onConsole = BackgammonatorConfig.getProperty(
			"backgammonator.debug.onConsole", true);

	private static boolean inFile = BackgammonatorConfig.getProperty(
			"backgammonator.debug.inFile", false);

	private static PrintStream consoleLog = System.out;

	private static PrintStream fileLog = null;
	private static File logFile = new File(BackgammonatorConfig.getProperty(
			"backgammonator.debug.file", "log.txt"));

	private static Debug instance = null;

	private Debug() {
		try {
			if (inFile) {
				if (logFile.exists()) {
					logFile.delete();
				}
				logFile.getParentFile().mkdirs();
				fileLog = new PrintStream(logFile);
			}
		} catch (FileNotFoundException e) {
			System.out
					.println("[ERROR] [Utils] Cannot initialize log file. Log in file will be disabled.");
		}
	}

	/**
	 * Returns reference to the instance of the Debug class
	 */
	public static Debug getInstance() {
		if (instance == null) instance = new Debug();
		return instance;
	}

	/**
	 * Logs the given info message for the specified module.
	 * 
	 * @param message the message to log
	 * @param moduleId the id of the module
	 */
	public synchronized void info(String message, int moduleId) {
		if (!debugOn) return;
		String debug = "[INFO] " + "[" + getModule(moduleId) + "] " + message;
		if (onConsole) {
			consoleLog.println(debug);
		}
		if (inFile) {
			fileLog.println(debug);
			fileLog.flush();
		}
	}

	/**
	 * Logs the given warning message for the specified module.
	 * 
	 * @param message the message to log
	 * @param moduleId the id of the module
	 * @param t Throwable object to log, can be null.
	 */
	public synchronized void warning(String message, int moduleId, Throwable t) {
		if (!debugOn) return;
		String debug = "[WARNING] " + "[" + getModule(moduleId) + "] "
				+ message;
		if (onConsole) {
			consoleLog.println(debug);
			if (t != null) t.printStackTrace(consoleLog);
		}
		if (inFile) {
			fileLog.println(debug);
			if (t != null) t.printStackTrace(fileLog);
			fileLog.flush();
		}
	}

	/**
	 * Logs the given error message for the specified module.
	 * 
	 * @param message the message to log
	 * @param moduleId the id of the module
	 * @param t Throwable object to log, can be null.
	 */
	public synchronized void error(String message, int moduleId, Throwable t) {
		if (!debugOn) return;
		String debug = "[ERROR] " + "[" + getModule(moduleId) + "] " + message;
		if (onConsole) {
			consoleLog.println(debug);
			if (t != null) t.printStackTrace(consoleLog);
		}
		if (inFile) {
			fileLog.println(debug);
			if (t != null) t.printStackTrace(fileLog);
			fileLog.flush();
		}
	}

	/**
	 * Closes the log file.
	 */
	public synchronized void close() {
		if (inFile) {
			fileLog.flush();
			fileLog.close();
		}
	}

	private static String getModule(int i) {
		String res;
		switch (i) {

		case PROTOCOL:
			res = "Protocol";
			break;
		case GAME_LOGIC:
			res = "Game Logic";
			break;
		case GAME_LOGGER:
			res = "Game Logger";
			break;
		case TOURNAMENT_LOGIC:
			res = "Turnament Logic";
			break;
		case TOURNAMENT_LOGGER:
			res = "Tournament Logger";
			break;
		case UTILS:
			res = "Utils";
			break;
		case DATABASE:
			res = "Database";
			break;
		case WEB_INTERFACE:
			res = "Web Interface";
			break;
		default:
			res = "Not Specified";
		}
		return res;
	}

}
