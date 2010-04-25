package backgammonator.core;

/**
 * Interface to provide format for the output of a single game. May be
 * implemented as writing plane text in a txt file, table in html document,
 * print on the console, etc.
 */

public interface Logger {

	void startGame(Player whitePlayer, Player blackPlayer);

	void logMove(PlayerMove move);

	void endGame(GameOverStatus status);

}
