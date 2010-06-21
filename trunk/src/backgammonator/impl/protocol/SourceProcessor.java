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
import backgammonator.sample.player.interfacce.SamplePlayer;

/**
 * Processing the uploaded source files to executables. It should support
 * processing of java and C/C++ sources. If any errors or warning occur during
 * the process, the should be shown to the user in an appropriate format.
 */
public class SourceProcessor {

	private static boolean windows = false;

	static {
		String osname = System.getProperty("os.name");
		windows = osname != null && osname.toLowerCase().startsWith("win");
	}

	/**
	 * Compiles the file and processes it to executable.
	 * 
	 * @param filePath The absolute path to the file.
	 * @return PlayerImpl
	 * @throws ProcessingException if any errors occur.
	 */
	public static Player processSource(String filePath)
			throws ProcessingException {
		File file = new File(filePath);
		if (!file.exists()) {
			Debug.getInstance().error(
					"The given file is not found: " + filePath, Debug.UTILS,
					null);
			throw new ProcessingException(ProcessingException.FILE_NOT_FOUND,
					"The given file is not found: " + filePath);
		}

		boolean isJava;
		if (file.getName().endsWith(".java")) isJava = true;
		else if (file.getName().endsWith(".c")
				|| file.getName().endsWith(".cpp")) isJava = false;
		else {
			Debug.getInstance().error(
					"The file must ends with .java or .c/.cpp: " + filePath,
					Debug.UTILS, null);
			throw new ProcessingException(
					ProcessingException.INVALID_EXTENSION,
					"The file must ends with .java or .c/.cpp: " + filePath);
		}

		Player result = null;
		Process compileProcess = null;
		try {
			if (isJava) {
				// check if the file has already been compiled
				File classFile = new File(file.getAbsolutePath().replace(
						".java", ".class"));

				if (!classFile.exists()) {
					compileProcess = Runtime.getRuntime().exec(
							windows ? "javac " + "\"" + file.getAbsolutePath()
									+ "\"" : "javac " + file.getAbsolutePath());

					// manage streams
					StreamCatcher errorGobbler = new StreamCatcher(
							compileProcess.getErrorStream(), "ERROR");
					StreamCatcher outputGobbler = new StreamCatcher(
							compileProcess.getInputStream(), "OUTPUT");
					errorGobbler.start();
					outputGobbler.start();

					int exitCode = compileProcess.waitFor();
					if (exitCode != 0) {
						Debug.getInstance().error(
								"Compilation error! Compile returned: "
										+ exitCode + "\n"
										+ errorGobbler.getMessage(),
								Debug.UTILS, null);
						throw new ProcessingException(
								ProcessingException.COMPILATION_ERROR,
								"Compilation error! Compile returned: "
										+ exitCode + "\n"
										+ errorGobbler.getMessage());
					}

					if (!classFile.exists()) {
						Debug.getInstance().error(
								"The compiled file is not found: " + filePath,
								Debug.UTILS, null);
						throw new ProcessingException(
								ProcessingException.COMPILED_FILE_NOT_FOUND,
								"The compiled file is not found: " + filePath);
					}
				}

				// manage result
				String mainClass = classFile.getName();
				mainClass = mainClass.substring(0, mainClass.indexOf("."));

				result = new ProtocolPlayerWrapper(
						(windows ? "java -cp " + "\"" + classFile.getParent()
								+ "\" " + mainClass : "java -cp "
								+ classFile.getParent() + " " + mainClass),
						classFile.getParentFile().getName());
			} else {
				String executableFileName = file.getAbsolutePath();
				int dot = executableFileName.lastIndexOf('.');
				executableFileName = executableFileName.substring(0, dot)
						+ ".exe";
				File executableFile = new File(executableFileName);

				// check if the executable already exists
				if (!executableFile.exists()) {
					compileProcess = Runtime.getRuntime().exec(
							windows ? "g++ -Wall -o " + "\""
									+ executableFile.getAbsolutePath()
									+ "\" \"" + file.getAbsolutePath() + "\""
									: "g++ -Wall -o "
											+ executableFile.getAbsolutePath()
											+ " " + file.getAbsolutePath());

					// manage streams
					StreamCatcher errorGobbler = new StreamCatcher(
							compileProcess.getErrorStream(), "ERROR");
					StreamCatcher outputGobbler = new StreamCatcher(
							compileProcess.getInputStream(), "OUTPUT");
					errorGobbler.start();
					outputGobbler.start();

					int exitCode = compileProcess.waitFor();
					if (exitCode != 0) {
						Debug.getInstance().error(
								"Compilation error! Compile returned: "
										+ exitCode + "\n"
										+ errorGobbler.getMessage(),
								Debug.UTILS, null);
						throw new ProcessingException(
								ProcessingException.COMPILATION_ERROR,
								"Compilation error! Compile returned: "
										+ exitCode + "\n"
										+ errorGobbler.getMessage());
					}

					if (!executableFile.exists()) {
						Debug.getInstance().error(
								"The compiled file is not found: " + filePath,
								Debug.UTILS, null);
						throw new ProcessingException(
								ProcessingException.COMPILED_FILE_NOT_FOUND,
								"The compiled file is not found: " + filePath);
					}
				}

				// manage result
				result = new ProtocolPlayerWrapper((windows ? "\""
						+ executableFile.getAbsolutePath() + "\""
						: executableFile.getAbsolutePath()), executableFile
						.getParentFile().getName());
			}

		} catch (Throwable e) {
			Debug.getInstance().error("Unexpected exception: " + filePath,
					Debug.UTILS, e);
			throw new ProcessingException(ProcessingException.UNEXPECTED_ERROR,
					e.getMessage());
		}
		return result;
	}

	/**
	 * Validates the given file.
	 * 
	 * @param filePath the full path to the file.
	 */
	public static String validateSource(String filePath) {
		Player toValidate = null;
		try {
			toValidate = processSource(filePath);
		} catch (ProcessingException pe) {
			return pe.getMessage();
		}

		Player samplePlayer = new SamplePlayer();
		Game game = GameManager.newGame(toValidate, samplePlayer, false);
		Object sync = new Object();

		String message = "Validation successful! No problems found.";
		GameOverStatus status;
		for (int i = 0; i < 3; i++) {
			status = game.start();
			if (!status.isNormal() && game.getWinner() == samplePlayer) {
				message = "Problems with the implemented protocol! Our test with"
						+ "sample player indicated: "
						+ status
						+ " at try "
						+ (i + 1);
				break;
			}
		}
		synchronized (sync) {
			sync.notifyAll();
		}
		return message;
	}

	/**
	 * Cleaning compilation files
	 * 
	 * @param deploymentdir player's implementation directory
	 */
	public static void cleanUp(String deploymentdir) {
		File dir = new File(deploymentdir);
		if (!dir.exists() || !dir.isDirectory()) {
			Debug.getInstance().error(
					"The given dir is not correct: " + deploymentdir,
					Debug.UTILS, null);
		}

		File[] allFiles = dir.listFiles();
		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i].getName().endsWith(".class")
					|| allFiles[i].getName().endsWith(".exe")) {
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
	@SuppressWarnings("unused")
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
				// System.out.println(type + ">" + line);
				output.append(line + "\r\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}