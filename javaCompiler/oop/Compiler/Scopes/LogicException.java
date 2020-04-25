package oop.ex6.Scopes;


/**
 * This class represents an Exceptions that are raised when a logic error occurred
 * @see Exception
 * @author shaharna13
 * @author dori203
 */

public class LogicException extends Exception{
	private static final long serialVersionUID = 1L;


	public LogicException(String massage){
		super(massage);
	}

	public LogicException(){
		super();
	}
}
