package backgammonator.util;

/**
 * Used for logging program behavior (warnings, errors, info) during execution.
 * The debug entry must contain level, message, Throwable object and module ID.
 */

public class Debug {

	private static boolean isDebugOn = false;

	public static int CORE_MODULE = 1;
	public static int GAME_MODULE = 2;
	public static int PROTOCOL_MODULE = 3;
	public static int TOURNAMENT_MODULE = 4;
	public static int UTIL_MODULE = 5;

	/**
	 * @param message
	 */
	public static void info(String message, int moduleId) {
		if (isDebugOn) {
			System.out.println("[Debug - info] " + "[" + //
					getModule(moduleId) + "]: " + message);
		}
	}

	/**
	 * @param message
	 * @param t
	 */
	public static void warning(String message, int moduleId, Throwable t) {
		if (isDebugOn) {
			System.out.println("[Debug - warning] " + "[" + getModule(moduleId)
					+ "]: " + message + "\n" + t.getMessage());
		}
	}

	/**
	 * @param message
	 * @param t
	 */
	public static void error(String message, int moduleId, Throwable t) {
		if (isDebugOn) {
			System.out.println("[Debug - error] " + "[" + getModule(moduleId) + //
					"]: " + message + "\n" + t.getMessage());
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
		default:
			res = "Module not specified";
		}
		return res;
	}

}
