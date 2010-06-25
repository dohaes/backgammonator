package backgammonator.impl.common;

import backgammonator.util.Debug;

/**
 * This class provide methods which manage locking the upload while a tournament
 * is started and unlocking it after it finishes.
 */
public final class Backgammonator {

	private final static long UPLOAD_TIMEOUT = 10000;

	private static int uploadsInProgress = 0;
	private static boolean uploadBlocked = false;

	private static Object synch = new Object();

	/**
	 * Locks the access to the uploads directory. If an upload is in process
	 * then the start of the tournament is postponed some seconds until the
	 * upload is finished. Called when a tournament is started.
	 * 
	 * @return <code>true</code> if the upload have been locked successfully or
	 *         have already been locked and <code>false</code> if any error
	 *         occurs while waiting for the uploads in progress to finish
	 */
	public static boolean blockUpload() {
		synchronized (synch) {
			// block the upload to indicate that there is request to start a
			// tournament
			uploadBlocked = true;
			// wait for the uploads in progress to finish
			while (uploadsInProgress > 0) {
				Debug.getInstance().info(
						"Waiting for uploads in progress to finish "
								+ "before starting the tournament...",
						Debug.WEB_INTERFACE);
				try {
					synch.wait(UPLOAD_TIMEOUT);
				} catch (InterruptedException ie) {
					Debug.getInstance().warning("Error while waiting",
							Debug.WEB_INTERFACE, ie);
					return false;
				}
			}
			// check if all uploads in progress have successfully finished
			if (uploadsInProgress > 0) {
				uploadsInProgress = 0;
				return false;
			}
			uploadsInProgress = 0;
			return true;
		}
	}

	/**
	 * Unlocks the access to the uploads directory. Called after the tournament
	 * is over.
	 */
	public static void unblockUpload() {
		synchronized (synch) {
			uploadBlocked = false;
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
			// if the upload has been blocked meanwhile then the current
			// thread will not proceed with the upload
			if (uploadBlocked) return false;
			uploadsInProgress++;
			return true;
		}
	}

	/**
	 * Unlocks the tournament. Called after the upload is over.
	 */
	public static void unblockTournament() {
		synchronized (synch) {
			uploadsInProgress--;
			// if there is a request to start a tournament then notify the
			// tournament to start if there are no more uploads in progress
			if (uploadBlocked && uploadsInProgress <= 0) synch.notifyAll();
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
