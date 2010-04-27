package backgammonator.util;

/**
 * Used for logging program behavior (warnings, errors, info) during execution.
 * The debug entry must contain level, message, Throwable object and module ID.
 */
public final class Debug {

	private static boolean isDebugOn = true; // TODO use system prop to configure debug

	public static final int CORE_MODULE = 1;
	public static final int GAME_MODULE = 2;
	public static final int PROTOCOL_MODULE = 3;
	public static final int TOURNAMENT_MODULE = 4;
	public static final int UTIL_MODULE = 5;
	public static final int LOGGER_MODULE = 6;

	private static Debug instance = null;

	private Debug() {
	}

	/**
	 * Returns reference to the instance of the Debug class
	 */
	public static Debug getInstance() {
		if (instance == null)
			instance = new Debug();
		return instance;
	}

	/**
	 * Logs the given info message for the specified module.
	 * 
	 * @param message the message to log
	 * @param moduleId the id of the module
	 */
	public synchronized void info(String message, int moduleId) {
		if (isDebugOn) {
			System.out.println("[INFO] " + "[" + //
					getModule(moduleId) + "]: " + message);
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
		if (isDebugOn) {
			System.out.println("[WARNING] " + "[" + getModule(moduleId) + "]: "
					+ message);
			if (t != null)
				t.printStackTrace();
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
		if (isDebugOn) {
			System.out.println("[ERROR] " + "[" + getModule(moduleId) + "]: "
					+ message);
			if (t != null)
				t.printStackTrace();
		}
	}

	private static String getModule(int i) {
		String res;
		switch (i) {
		case 1:
			res = "Core Module";
			break;
		case 2:
			res = "Game Module";
			break;
		case 3:
			res = "Protocol Module";
			break;
		case 4:
			res = "Turnament Module";
			break;
		case 5:
			res = "Util Module";
			break;
		case 6:
			res = "Logger Module";
			break;
		default:
			res = "Module not specified";
		}
		return res;
	}

}
