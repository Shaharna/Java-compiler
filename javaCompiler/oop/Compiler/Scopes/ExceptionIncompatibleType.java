package oop.ex6.Scopes;

/**
 * This class represents an Exceptions that are raised when two variables with different type are declared
 * in the same scope.
 * @see LogicException
 * @author shaharna13
 * @author dori203
 */

public class ExceptionIncompatibleType extends LogicException {
	private static final long serialVersionUID = 1L;

	private static final String Not_Valid_Assignment_ERROR =
			"Error: Incompatible Types ";

	/**
	 * Constructor
	 */
	public ExceptionIncompatibleType(){
		super(Not_Valid_Assignment_ERROR);
	}
}
