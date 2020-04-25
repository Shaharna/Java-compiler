package oop.ex6.Scopes;

/**
 * This class represents an Exceptions that are raised when an error occured while declaring or
 * assingning params, there are additional informative messages for each case.
 * in the same scope.
 * @see LogicException
 * @author shaharna13
 * @author dori203
 */

 public class ExceptionVariableDeclarationAndAssignment extends LogicException {
	private static final long serialVersionUID = 1L;

	private static final String VARIABLE_DECLARED_ERROR ="Error: The assignment is illegal";

	public ExceptionVariableDeclarationAndAssignment(){
		super(VARIABLE_DECLARED_ERROR);
	}

	public ExceptionVariableDeclarationAndAssignment(String message ){
		super(message);
	}
}
