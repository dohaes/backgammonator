package backgammonator.impl.processor.python;

import java.io.File;

import backgammonator.impl.protocol.ProtocolPlayerWrapper;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilder;
import backgammonator.lib.processor.PlayerBuilderException;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;
import backgammonator.util.StreamParser;

/**
 * Implementation of the {@link PlayerBuilder} interface for Python sources.
 * 
 * @see PlayerBuilder
 */
public class PythonPlayerBuilder implements PlayerBuilder {

	private static PlayerBuilder instance = null;
	/**
	 * @see PlayerBuilder#build(File)
	 */
	@Override
	public Player build(File source) throws PlayerBuilderException {
		// check if the file has already been compiled
		File pycFile = new File(source.getAbsolutePath().replace(".py", ".pyc"));

		Process compileProcess = null;
		try {
			if (!pycFile.exists()) {
				compileProcess = Runtime.getRuntime().exec(
				// TODO figure out where the compile.py file will be placed and
				// fix the path to it.
						BackgammonatorConfig.windows ? "python compile.py "
								+ "\"" + source.getAbsolutePath() + "\""
								: "python compile.py "
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

				if (!pycFile.exists()) {
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
			String mainClass = pycFile.getName();
			mainClass = mainClass.substring(0, mainClass.indexOf("."));

			return new ProtocolPlayerWrapper(
					(BackgammonatorConfig.windows ? "\""
							+ pycFile.getAbsolutePath() + "\"" : pycFile
							.getAbsolutePath()), pycFile.getParentFile()
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
		if (instance == null) instance = new PythonPlayerBuilder();
		return instance;
	}

}
