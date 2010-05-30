package backgammonator.impl.protocol;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
			Debug.getInstance().error("The given file is not found: " + filePath,
					Debug.UTILS, null);
			return null;
		}

		boolean isJava;
		if (file.getName().endsWith(".java")) isJava = true;
		else if (file.getName().endsWith(".c")) isJava = false;
		else {
			Debug.getInstance()
					.error("The file must ends with .java or .c: " + filePath,
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
				// TODO notify user for compile error
				if (exitCode != 0) {
					Debug.getInstance().error("Compile returned: " + exitCode,
							Debug.UTILS, null);
					return null;
				}
				File classFile = new File(file.getAbsolutePath().replace(".java",
						".class"));
				if (!classFile.exists()) {
					Debug.getInstance().error(
							"The compiled file is not found: " + filePath, Debug.UTILS, null);
					return null;
				}
				// manage result
				String mainClass = classFile.getName();
				mainClass = mainClass.substring(0, mainClass.indexOf("."));

				result = new ProtocolPlayer("java -cp " + classFile.getParent() + " "
						+ mainClass, classFile.getParentFile().getName());
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
	 * Cleaning compilation files
	 * 
	 * @param deproylemntdir player's implementation dir
	 */
	public static void cleanUp(String deproylemntdir) {
		File dir = new File(deproylemntdir);
		if (!dir.exists() || !dir.isDirectory()) {
			Debug.getInstance().error(
					"The given dir is not correct: " + deproylemntdir, Debug.UTILS, null);
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
			while ((line = br.readLine()) != null)
				System.out.println(type + ">" + line);
			output.append(line + "\r\n");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}