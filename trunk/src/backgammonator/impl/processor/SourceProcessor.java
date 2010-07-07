package backgammonator.impl.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import backgammonator.impl.game.GameManager;
import backgammonator.impl.processor.c.CPlayerBuilder;
import backgammonator.impl.processor.java.JavaPlayerBuilder;
import backgammonator.impl.processor.python.PythonPlayerBuilder;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilder;
import backgammonator.lib.processor.PlayerBuilderException;
import backgammonator.util.Debug;
import backgammonator.sample.player.interfacce.SamplePlayer;

/**
 * Processing the uploaded source files to executables. It should support
 * processing of java and C/C++ sources. If any errors or warning occur during
 * the process, the should be shown to the user in an appropriate format.
 */
public class SourceProcessor {

	private static Map<String, PlayerBuilder> builders;

	static {
		// initialize builders
		builders = new HashMap<String, PlayerBuilder>(4);
		builders.put("c", CPlayerBuilder.getInstance());
		builders.put("cpp", CPlayerBuilder.getInstance());
		builders.put("java", JavaPlayerBuilder.getInstance());
		builders.put("py", PythonPlayerBuilder.getInstance());
	}

	/**
	 * Compiles the file and processes it to executable.
	 * 
	 * @param filePath The absolute path to the file.
	 * @return PlayerImpl
	 * @throws PlayerBuilderException if any errors occur during compiling and
	 *             building the source.
	 */
	public static Player processSource(String filePath)
			throws PlayerBuilderException {
		File file = new File(filePath);
		if (!file.exists()) {
			Debug.getInstance().error(
					"The given file is not found: " + filePath, Debug.UTILS,
					null);
			throw new PlayerBuilderException(
					PlayerBuilderException.FILE_NOT_FOUND,
					"The given file is not found: " + filePath);
		}

		int lastDot = filePath.lastIndexOf('.');
		if (lastDot <= 0 || lastDot >= filePath.length() - 1) {
			Debug.getInstance().error("Invalid file name : " + filePath,
					Debug.UTILS, null);
			throw new PlayerBuilderException(
					PlayerBuilderException.INVALID_EXTENSION,
					"Invalid file name : " + filePath);
		}
		String extension = filePath.substring(lastDot + 1);
		PlayerBuilder builder = builders.get(extension);
		if (builder == null) {
			// no mapped builder for this extension
			// so the file extension is invalid
			Debug.getInstance().error(
					"The file must ends with .java, .c/.cpp or .py: "
							+ filePath, Debug.UTILS, null);
			throw new PlayerBuilderException(
					PlayerBuilderException.INVALID_EXTENSION,
					"The file must ends with .java, .c/.cpp or .py: "
							+ filePath);
		}

		// build the player
		return builder.build(file);
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
		} catch (PlayerBuilderException pe) {
			return pe.getMessage();
		}

		Player samplePlayer = new SamplePlayer();
		Game game = GameManager.newGame(toValidate, samplePlayer, false);

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
