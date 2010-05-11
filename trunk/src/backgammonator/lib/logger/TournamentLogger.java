package backgammonator.lib.logger;

import java.util.List;

import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.TournamentType;


/**
 * Interface to provide format for the output of a single tournament. May be
 * implemented as writing plane text in a txt file, table in html document,
 * print on the console, etc.
 */
public interface TournamentLogger {

	/**
	 * Notifies the logger that a tournament has started with the specified list
	 * of players. The logger should prepare for logging.
	 * 
	 * @param players
	 *            is the list of players, whose will be participate in the
	 *            tournament.
	 * @param type
	 *            is the type of the tournament.
	 */
	void startTournament(List<Player> players, TournamentType type);

	/**
	 * Logs a single game of the tournament.
	 * 
	 * @param whitePlayer
	 *            is the white player.
	 * @param blackPlayer
	 *            is the black player.
	 * @param status
	 *            is the game over status towards the white player.
	 */
	void logGame(Player whitePlayer, Player blackPlayer, GameOverStatus status);

	/**
	 * Notifies the logger that the tournament has finished giving the result.
	 */
	void endTournament();

	/**
	 * Returns the name of the output file or null when not available.
	 * 
	 * @return the name of the output file or null when not available.
	 */
	String getFilename();

	/**
	 * Returns the timestamp of the logged tournament or null when not
	 * available.
	 * 
	 * @return the timestamp of the logged tournament or null when not
	 *         available.
	 */
	public String getTournamentTimestamp();

}
