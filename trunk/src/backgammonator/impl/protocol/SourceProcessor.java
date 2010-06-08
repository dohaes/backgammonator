package backgammonator.impl.protocol;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import backgammonator.impl.game.GameManager;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.util.Debug;

/**
 * Processing the uploaded source files to executables. It should support
 * processing of java and C/C++ sources. If any errors or warning occur during
 * the process, the should be shown to the user in an appropriate format.
 */
public class SourceProcessor {

	/**
	 * Compiles the file and process it to executable.
	 * 
	 * @param filePath The absolute path to the file
	 * @return PlayerImpl
	 * @throws IllegalArgumentException when the given file does not exists
	 */
	public static Player processFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			Debug.getInstance().error(
					"The given file is not found: " + filePath, Debug.UTILS,
					null);
			return null;
		}

		boolean isJava;
		if (file.getName().endsWith(".java")) isJava = true;
		else if (file.getName().endsWith(".c")) isJava = false;
		else {
			Debug.getInstance().error(
					"The file must ends with .java or .c: " + filePath,
					Debug.UTILS, null);
			return null;
		}

		Player result = null;
		Process compileProcess = null;
		try {
			if (isJava) {
				compileProcess = Runtime.getRuntime().exec(
						"javac " + file.getAbsolutePath());

				// TODO optimizing threads! maybe we need threadpool ?
				// manage streams
				StreamCatcher errorGobbler = new StreamCatcher(compileProcess
						.getErrorStream(), "ERROR");
				StreamCatcher outputGobbler = new StreamCatcher(compileProcess
						.getInputStream(), "OUTPUT");
				errorGobbler.start();
				outputGobbler.start();

				int exitCode = compileProcess.waitFor();
				if (exitCode != 0) {
					Debug.getInstance().error("Compile returned: " + exitCode,
							Debug.UTILS, null);
					return null;
				}
				File classFile = new File(file.getAbsolutePath().replace(
						".java", ".class"));
				if (!classFile.exists()) {
					Debug.getInstance().error(
							"The compiled file is not found: " + filePath,
							Debug.UTILS, null);
					return null;
				}
				// manage result
				String mainClass = classFile.getName();
				mainClass = mainClass.substring(0, mainClass.indexOf("."));

				result = new ProtocolPlayer("java -cp " + classFile.getParent()
						+ " " + mainClass, classFile.getParentFile().getName());
			} else {
				// TODO manage c++ files
			}
		} catch (Throwable e) {
			Debug.getInstance().error("Unexpected exception: " + filePath,
					Debug.UTILS, e);
			return result;
		}
		return result;
	}

	/**
	 * Validate he given file
	 */
	public static String validateSource(String filePath) {
		File file = new File(filePath);
		boolean isJava;
		if (file.getName().endsWith(".java")) isJava = true;
		else if (file.getName().endsWith(".c")) isJava = false;
		else {
			return "The file must ends with .java or .c:";
		}

		Player result = null;
		Process compileProcess = null;
		try {
			if (isJava) {
				compileProcess = Runtime.getRuntime().exec(
						"javac " + file.getAbsolutePath());
				StreamCatcher errorGobbler = new StreamCatcher(compileProcess
						.getErrorStream(), "ERROR");
				errorGobbler.start();

				int exitCode = compileProcess.waitFor();
				if (exitCode != 0) {
					return "Compilation Error!: \r\n"
							+ errorGobbler.getMessage();
				}
				File classFile = new File(file.getAbsolutePath().replace(
						".java", ".class"));
				if (!classFile.exists()) {
					return "Error with the compilation!";
				}

				// manage result
				String mainClass = classFile.getName();
				mainClass = mainClass.substring(0, mainClass.indexOf("."));

				result = new ProtocolPlayer("java -cp " + classFile.getParent()
						+ " " + mainClass, classFile.getParentFile().getName());
			} else {
				// TODO postponed for now!
			}
		} catch (Exception e) {
			Debug.getInstance().error("Unexpected exception: " + filePath,
					Debug.UTILS, e);
			return "Exception:" + e.getMessage();
		}

		String samplesPath = "sample/backgammonator/sample/player/protocol/java"
				.replace('/', File.separatorChar);
		Player player2 = processFile(samplesPath + File.separator
				+ "SamplePlayer.java");

		Game game = GameManager.newGame(result, player2, true);
		Object sync = new Object();

		GameThread gameThead = new GameThread(game, sync, player2);
		gameThead.start();
		synchronized (sync) {
			try {
				sync.wait(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return gameThead.getMessage();
	}

	public static void main(String[] args) {
		String res = validateSource("sample\\backgammonator\\sample\\player\\protocol\\java\\SamplePlayer.java");
		System.out.println(res);
	}

	/**
	 * Cleaning compilation files
	 * 
	 * @param deproylemntdir player's implementation dir
	 */
	public static void cleanUp(String deproylemntdir) {
		File dir = new File(deproylemntdir);
		if (!dir.exists() || !dir.isDirectory()) {
			Debug.getInstance().error(
					"The given dir is not correct: " + deproylemntdir,
					Debug.UTILS, null);
		}

		File[] allFiles = dir.listFiles();
		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i].getName().endsWith(".class")) {
				allFiles[i].delete();
			}
		}
	}
}

/**
 * Dumps error and out streams of the compile process
 */
class StreamCatcher extends Thread {
	private InputStream is;
	private String type;
	private StringBuffer output = new StringBuffer();

	public String getMessage() {
		return output.toString();
	}

	/**
	 * Constructor with two parameters
	 * 
	 * @param is
	 * @param type
	 */
	StreamCatcher(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(type + ">" + line);
				output.append(line + "\r\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}

class GameThread extends Thread {
	private Game game;
	private Object sync;
	private String message;
	private GameOverStatus status;
	private Player player2;

	GameThread(Game game, Object sync, Player player2) {
		this.game = game;
		this.sync = sync;
		this.player2 = player2;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		message = "Vefification succeeded successfully! No problem found.";
		for (int i = 0; i < 5; i++) {
			status = game.start();
			if (status != GameOverStatus.NORMAL && game.getWinner() == player2) {
				message = "Problems with the implemented protocol. Our test with"
						+ "sample player indicated: "
						+ status
						+ " at "
						+ i
						+ " try";
				break;
			}
		}
		synchronized (sync) {
			sync.notifyAll();
		}
	}
}