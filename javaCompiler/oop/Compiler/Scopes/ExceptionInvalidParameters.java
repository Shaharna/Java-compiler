package oop.ex6.Scopes;

/**
 * This class represents an Exceptions that are raised when there is invalid number of parameters in a
 * function call.
 * @see LogicException
 * @author shaharna13
 * @author dori203
 */
public class ExceptionInvalidParameters extends LogicException {
	private static final long serialVersionUID = 1L;

	private static final String WRONG_NUMBER_OF_PARAMS =
			"Error: You have an error in your parameters while calling the function " +
					"(Type or number of parameters";

	ExceptionInvalidParameters(){
		super(WRONG_NUMBER_OF_PARAMS);
	}

	ExceptionInvalidParameters(String message){
		super(message);
	}
}