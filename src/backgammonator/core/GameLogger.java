package backgammonator.core;

/**
 * Interface to provide format for the output of a single game. May be
 * implemented as writing plane text in a txt file, table in html document,
 * print on the console, etc.
 */

public interface GameLogger {

	void startGame(Player whitePlayer, Player blackPlayer);

	void logMove(PlayerMove move, PlayerColor color, Dice dice, int hit, int bornOff, boolean invalid);

	void endGame(GameOverStatus status, PlayerColor winner);

}
