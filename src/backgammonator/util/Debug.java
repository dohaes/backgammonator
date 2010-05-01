package backgammonator.util;

/**
 * Used for logging program behavior (warnings, errors, info) during execution.
 * The debug entry must contain level, message, Throwable object and module ID.
 */
public final class Debug {

	public static final int PROTOCOL = 1;
	public static final int GAME_LOGIC = 2;
	public static final int GAME_LOGGER = 4;
	public static final int TOURNAMENT_LOGIC = 5;
	public static final int TOURNAMENT_LOGGER = 6;
	public static final int UTILS = 7;

	private static boolean isDebugOn = true; // TODO use system prop to configure debug
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
		default:
			res = "Not Specified";
		}
		return res;
	}

}
