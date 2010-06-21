package backgammonator.lib.tournament;

/**
 * Thrown if any error occur during tournament execution.
 */
public class TournamentException extends Exception {

	private static final long serialVersionUID = 8796961461633242228L;

	/**
	 * Constructs a new object.
	 * 
	 * @param message message.
	 */
	public TournamentException(String message) {
		super(message);
	}
}
