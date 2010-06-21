package backgammonator.impl.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import backgammonator.lib.game.Game;
import backgammonator.util.Debug;

/**
 * Runs a single game between two players. The players are to be source files
 * implementing the defined protocol. The absolute paths to that source files
 * should be given as arguments in an appropriate way.
 */
public class Main {

	private static File whiteBase = new File("player1"); // first player base
	// directory
	private static File blackBase = new File("player2"); // second player base

	// directory

	/**
	 * Main method for testing two backgammon players
	 * 
	 * @param args the two absolute paths to that source files
	 */
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			System.out
					.println("Usage : java -jar full_path_to_backgammonatorLibrary.jar "
							+ "<full_path_to_player1> <full_path_to_player2> "
							+ "[<number_of_games>]");
			return;
		}

		int runs = 1;

		if (args.length == 3) try {
			runs = Integer.parseInt(args[2]);
		} catch (NumberFormatException nfe) {
			Debug.getInstance().error(
					"Error parsing number of runs : " + args[2], Debug.DEMO,
					nfe);
		}

		// check if the given files exist before copying
		File whitePlayer = new File(args[0]);
		if (!whitePlayer.exists()) {
			Debug.getInstance().error("File not found : " + whitePlayer,
					Debug.DEMO, null);
		}
		File blackPlayer = new File(args[1]);
		if (!blackPlayer.exists()) {
			Debug.getInstance().error("File not found : " + blackPlayer,
					Debug.DEMO, null);
		}

		// initialize the game with the copied sources
		Game game = null;
		try {
			game = GameManager.newGame(copy(whitePlayer, whiteBase), copy(
					blackPlayer, blackBase), true);
		} catch (Throwable t) {
			Debug.getInstance().error("Error while initializing the game",
					Debug.DEMO, t);
			return;
		}

		// execute as many games as defined
		for (int i = 0; i < runs; i++) {
			game.start();
		}

		//clean
		if (!clean()) {
			Debug.getInstance().error("Error while cleaning up",
					Debug.DEMO, null);
		}
	}

	/**
	 * Copies the source file given as argument to the demo.
	 * 
	 * @param source the source file given as argument
	 * @param toDir the player base directory
	 * @return the full path to the copied source file
	 */
	private static String copy(File source, File toDir) throws IOException {
		if (!toDir.exists()) toDir.mkdirs();
		String destFilename = toDir.getAbsolutePath();
		if (!destFilename.endsWith(File.separator)) destFilename += File.separator;
		destFilename += source.getName();

		FileInputStream fis = new FileInputStream(source);
		FileOutputStream fos = new FileOutputStream(new File(destFilename));

		int len = 0;
		byte[] buff = new byte[24];
		try {
			while ((len = fis.read(buff, 0, 24)) != -1) {
				fos.write(buff, 0, len);
			}
		} finally {
			fis.close();
			fos.flush();
			fos.close();
		}

		return destFilename;
	}

	private static boolean clean() {
		return ((whiteBase.exists() && delete(whiteBase))
				& (blackBase.exists()) && delete(blackBase));
	}

	private static boolean delete(File file) {
		if (file.isFile()) return file.delete();
		File[] files = file.listFiles();
		if (files == null || files.length == 0) return file.delete();
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return file.delete();
	}

}
