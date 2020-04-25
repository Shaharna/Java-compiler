package oop.ex6.Regex;

/**
 * Exception class for exception caused by illegal actions found by regex.
 * @author shaharna13 dori203
 * @see Exception
 */
public class RegexException extends Exception {

	private final static String message = "a regex exception";
	/**
	 * an exception containing the error
	 */
	public RegexException(){
		super(message);
	}

	/**
	 * an exception containing the error
	 */
	public RegexException(String massage){
		super(massage);
	}

}
