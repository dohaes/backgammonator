package backgammonator.impl.protocol;

/**
 * Custom exception which can be thrown during source processing.
 */
public class ProcessingException extends Exception {

	private static final long serialVersionUID = 6926729429088689254L;
	private int code;

	/**
	 * Indicates that the specified file given for processing is not found.
	 */
	public static final int FILE_NOT_FOUND = 1;

	/**
	 * Indicates that the specified file given for processing has invalid
	 * extension i.e. it is not .java or .c file.
	 */
	public static final int INVALID_EXTENSION = 2;

	/**
	 * Indicates that the output file of the compilation of the specified file
	 * given for processing is not found.
	 */
	public static final int COMPILED_FILE_NOT_FOUND = 3;

	/**
	 * Indicates that a compilation error occured while processing the specified
	 * file.
	 */
	public static final int COMPILATION_ERROR = 4;

	/**
	 * Indicates that an unexpected error occured while processing the specified
	 * file.
	 */
	public static final int UNEXPECTED_ERROR = 5;

	/**
	 * Constructs a new {@link ProcessingException} object.
	 * 
	 * @param code the error code.
	 * @param message the message for the error
	 */
	public ProcessingException(int code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * Returns the error code.
	 * 
	 * @return the error code.
	 */
	public int getCode() {
		return code;
	}

}
