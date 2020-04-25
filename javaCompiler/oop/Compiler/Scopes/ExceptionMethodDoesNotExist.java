package oop.ex6.Scopes;
/**
 * This class represents an Exceptions that are raised when a function that does not exist in the code
 * is called.
 * @see LogicException
 * @author shaharna13
 * @author dori203
 */


public class ExceptionMethodDoesNotExist extends LogicException {
	private static final long serialVersionUID = 1L;

	private static final String NO_SUCH_METHOD =
			"Error: you called a method that does not exists in the code ";

	ExceptionMethodDoesNotExist(){
		super(NO_SUCH_METHOD);
	}
}
