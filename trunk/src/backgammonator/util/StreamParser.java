package backgammonator.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Gets the content of an input stream as string.
 */
public final class StreamParser extends Thread {
	private InputStream is;
	private StringBuffer output;

	/**
	 * Returns the content of the stream as string message.
	 */
	public String getMessage() {
		return output.toString();
	}

	/**
	 * Constructs new {@link StreamParser} object.
	 * 
	 * @param is the given input stream.
	 */
	public StreamParser(InputStream is) {
		this.is = is;
		output = new StringBuffer();
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				output.append(line + "\r\n");
			}
		} catch (Throwable ioe) {
			Debug.getInstance().warning("Error parsing stream", Debug.UTILS,
					ioe);
		} finally {
			try {
				if (br != null) br.close();
				is.close();
			} catch (Throwable t) {
				Debug.getInstance().warning("Error closing stream",
						Debug.UTILS, t);
			}
		}
	}
}