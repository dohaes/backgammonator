package backgammonator.impl.processor.c;

import java.io.File;

import backgammonator.impl.protocol.ProtocolPlayerWrapper;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilder;
import backgammonator.lib.processor.PlayerBuilderException;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;
import backgammonator.util.StreamParser;

/**
 * Implementation of the {@link PlayerBuilder} interface for C/C++ sources.
 * 
 * @see PlayerBuilder
 */
public class CPlayerBuilder implements PlayerBuilder {
	
	private static PlayerBuilder instance = null;

	/**
	 * @see PlayerBuilder#build(File)
	 */
	@Override
	public Player build(File source) throws PlayerBuilderException {
		String executableFileName = source.getAbsolutePath();
		int dot = executableFileName.lastIndexOf('.');
		executableFileName = executableFileName.substring(0, dot) + ".exe";
		File executableFile = new File(executableFileName);

		Process compileProcess = null;
		try {
			// check if the executable already exists
			if (!executableFile.exists()) {
				compileProcess = Runtime.getRuntime().exec(
						BackgammonatorConfig.windows ? "g++ -Wall -o " + "\""
								+ executableFile.getAbsolutePath() + "\" \""
								+ source.getAbsolutePath() + "\""
								: "g++ -Wall -o "
										+ executableFile.getAbsolutePath()
										+ " " + source.getAbsolutePath());

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

				if (!executableFile.exists()) {
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
			return new ProtocolPlayerWrapper(
					(BackgammonatorConfig.windows ? "\""
							+ executableFile.getAbsolutePath() + "\""
							: executableFile.getAbsolutePath()), executableFile
							.getParentFile().getName());
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
		if (instance == null) instance = new CPlayerBuilder();
		return instance;
	}
}
