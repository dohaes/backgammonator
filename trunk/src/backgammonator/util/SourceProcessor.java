package backgammonator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.FileNotFoundException;

import backgammonator.game.Player;

/**
 * Processing the uploaded source files to executables. It should support
 * processing of java and C/C++ sources. If any errors or warning occur during
 * the process, the should be shown to the user in an appropriate format.
 * 
 */
public class SourceProcessor {

	/**
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Player processFile(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		if (!file.exists())
			throw new FileNotFoundException("The file does not exists!");
		Player result = null;
		Process compileProcess = null;
		try {
			compileProcess = Runtime
					.getRuntime()
					.exec(
							"cmd.exe /C javac "
									+ file.getAbsolutePath()
									+ " -classpath ../../../../lib/backgammonlibrary.jar");
			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(compileProcess
					.getErrorStream(), "ERROR");

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(compileProcess
					.getInputStream(), "OUTPUT");

			// kick them off
			errorGobbler.start();
			outputGobbler.start();

			// TODO fix file path!
			// result = new PlayerImpl(
			// Runtime.getRuntime().exec(
			// "cmd.exe /C java "
			// + file.getAbsolutePath()
			// + " -classpath ../../../../lib/backgammonlibrary.jar"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			processFile("C:\\Develop\\eclipse\\workspace\\backgammonator\\test\\backgammonator\\test\\players\\AbstractTestPlayer.java");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class StreamGobbler extends Thread {
	InputStream is;
	String type;
	StringBuffer output = new StringBuffer();

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

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

	public String getOutput() {
		return this.output.toString();
	}
}