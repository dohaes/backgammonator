package backgammonator.impl.protocol;

public class ProcessingException extends Exception {
	
	public static final int FILE_NOT_FOUND =1;
	public static final int INVALID_EXTENSION =2;
	public static final int COMPILED_FILE_NOT_FOUND =3;
	public static final int COMPILATION_ERROR =4;
	public static final int UNEXPECTED_ERROR =5;
	
	private int code;
	private String message = "Unexpected error";
	
	public ProcessingException(int code, String message) {
		this.code = code;
		this.message = message;		
	}
	
	public int getCode(){
		return code;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	

}
