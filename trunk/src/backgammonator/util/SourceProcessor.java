package backgammonator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.FileNotFoundException;

import javax.activation.UnsupportedDataTypeException;

import backgammonator.impl.protocol.PlayerFactory;
import backgammonator.lib.game.Player;

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
	 * @throws UnsupportedDataTypeException 
	 */
	public static Player processFile(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		if (!file.exists())
			throw new FileNotFoundException("Incorrect file!");
		
		boolean isJava;
		if(file.getName().endsWith(".java")) isJava = true;
		else if(file.getName().endsWith(".c")) isJava = false;
		else throw new IllegalArgumentException("The file must ends with .java or .c");
		
		Player result = null;
		Process compileProcess = null;
		try {
			if(isJava) {
				compileProcess = Runtime.getRuntime().exec(
								"javac " + file.getAbsolutePath()
										+ " -classpath lib/backgammonlibrary.jar");
				
				// manage streams
				StreamGobbler errorGobbler = new StreamGobbler(compileProcess
						.getErrorStream(), "ERROR");
				StreamGobbler outputGobbler = new StreamGobbler(compileProcess
						.getInputStream(), "OUTPUT");
				errorGobbler.start();
				outputGobbler.start();
				
				File classFile = new File(file.getAbsolutePath().replace(".java", ".class"));
				if (!classFile.exists())
					throw new FileNotFoundException("Incorrect file!");
				
				//manage result
				 result = PlayerFactory.newPlayer("java " + classFile.getAbsolutePath()
				 + " -classpath lib/backgammonlibrary.jar", classFile.getParentFile().getName());
			} else {
				//TODO manage c++ files
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			processFile("C:\\Develop\\eclipse\\workspace\\backgammonator\\sample\\backgammonator\\sample\\players\\interfacce\\AbstractSamplePlayer.java");
		} catch (Exception e) {
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