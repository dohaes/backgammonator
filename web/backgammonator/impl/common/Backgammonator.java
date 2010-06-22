package backgammonator.impl.common;

import backgammonator.util.Debug;

/**
 * This class provide methods which manage locking the upload while a tournament
 * is started and unlocking it after it finishes.
 */
public final class Backgammonator {

	private final static long UPLOAD_TIMEOUT = 10000;

	private static int waiting = 0;
	private static boolean uploadBlocked = false;
	private static boolean tournamentBlocked = false;

	private static Object synch = new Object();

	/**
	 * Locks the access to the uploads directory. If an upload is in process
	 * then the start of the tournament is postponed some seconds until the
	 * upload is finished. Called when a tournament is started.
	 */
	public static void blockUpload() {
		synchronized (synch) {
			if (tournamentBlocked) {
				try {
					waiting++;
					synch.wait(UPLOAD_TIMEOUT);
				} catch (InterruptedException ie) {
					Debug.getInstance().warning("Error while waiting",
							Debug.WEB_INTERFACE, ie);
					tournamentBlocked = false;
				}
				waiting--;
			}
			// tournament is unblocked
			// block the upload
			uploadBlocked = true;
		}
	}

	/**
	 * Unlocks the access to the uploads directory. Called after the tournament
	 * is over.
	 */
	public static void unblockUpload() {
		synchronized (synch) {
			// unblock the upload and notify the tournament to start
			uploadBlocked = false;
			if (waiting > 0) synch.notifyAll();
		}
	}

	/**
	 * Locks the tournament. Called prior to doing the actual upload.
	 * 
	 * @return <code>true</code> if the tournament have been locked successfully
	 *         or have already been locked and <code>false</code> if the upload
	 *         have been locked meanwhile.
	 */
	public static boolean blockToutnament() {
		synchronized (synch) {
			if (uploadBlocked) return false;
			if (!tournamentBlocked) tournamentBlocked = true;
			return true;
		}
	}

	/**
	 * Unlocks the tournament. Called after the upload is over.
	 */
	public static void unblockTournament() {
		synchronized (synch) {
			if (tournamentBlocked) {
				// unblock the tournament
				tournamentBlocked = false;
			}
		}
	}

	/**
	 * Indicates if the uploads directory access is locked.
	 * 
	 * @return <code>true</code> if the access is locked or <code>false</code>
	 *         otherwise.
	 */
	public static boolean isUploadBlocked() {
		synchronized (synch) {
			return uploadBlocked;
		}

	}
}
