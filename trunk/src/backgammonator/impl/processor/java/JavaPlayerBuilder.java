package backgammonator.impl.processor.java;

import java.io.File;

import backgammonator.impl.protocol.ProtocolPlayerWrapper;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilder;
import backgammonator.lib.processor.PlayerBuilderException;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;
import backgammonator.util.StreamParser;

/**
 * Implementation of the {@link PlayerBuilder} interface for Java sources.
 * 
 * @see PlayerBuilder
 */
public class JavaPlayerBuilder implements PlayerBuilder {
	
	private static PlayerBuilder instance = null;

	/**
	 * @see PlayerBuilder#build(File)
	 */
	@Override
	public Player build(File source) throws PlayerBuilderException {
		// check if the file has already been compiled
		File classFile = new File(source.getAbsolutePath().replace(".java",
				".class"));

		Process compileProcess = null;
		try {
			if (!classFile.exists()) {
				compileProcess = Runtime.getRuntime().exec(
						BackgammonatorConfig.windows ? "javac " + "\""
								+ source.getAbsolutePath() + "\"" : "javac "
								+ source.getAbsolutePath());

				// manage streams
				StreamParser errorGobbler = new StreamParser(compileProcess
						.getErrorStream());
				StreamParser outputGobbler = new StreamParser(compileProcess
						.getInputStream());
				errorGobbler.start();
				outputGobbler.start();

				int exitCode = compileProcess.waitFor();
				if (exitCode != 0) {
					Debug.getInstance().error(
							"Compilation error! Compile returned: " + exitCode
									+ "\n" + errorGobbler.getMessage(),
							Debug.UTILS, null);
					throw new PlayerBuilderException(
							PlayerBuilderException.COMPILATION_ERROR,
							"Compilation error! Compile returned: " + exitCode
									+ "\n" + errorGobbler.getMessage());
				}

				if (!classFile.exists()) {
					Debug.getInstance().error(
							"The compiled file is not found: "
									+ source.getAbsolutePath(), Debug.UTILS,
							null);
					throw new PlayerBuilderException(
							PlayerBuilderException.COMPILED_FILE_NOT_FOUND,
							"The compiled file is not found: "
									+ source.getAbsolutePath());
				}
			}

			// manage result
			String mainClass = classFile.getName();
			mainClass = mainClass.substring(0, mainClass.indexOf("."));

			return new ProtocolPlayerWrapper(
					(BackgammonatorConfig.windows ? "java -cp " + "\""
							+ classFile.getParent() + "\" " + mainClass
							: "java -cp " + classFile.getParent() + " "
									+ mainClass), classFile.getParentFile()
							.getName());

		} catch (Throwable e) {
			Debug.getInstance().error(
					"Unexpected exception: " + source.getAbsolutePath(),
					Debug.UTILS, e);
			throw new PlayerBuilderException(
					PlayerBuilderException.UNEXPECTED_ERROR, e.getMessage());
		}

	}
	
	/**
	 * Retrieves the singleton instance.
	 */
	public static PlayerBuilder getInstance() {
		if (instance == null) instance = new JavaPlayerBuilder();
		return instance;
	}

}
