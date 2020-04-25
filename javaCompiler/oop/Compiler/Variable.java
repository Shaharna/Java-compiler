package oop.ex6;

import oop.ex6.Regex.Patterns.*;
import oop.ex6.Scopes.ExceptionIncompatibleType;
import oop.ex6.Scopes.ExceptionVariableDeclarationAndAssignment;
import oop.ex6.Scopes.LogicException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for variable objects.
 */
public class Variable {

	//Error message
	private static final String A_FINAL_VARIABLE_WAS_DECLARED_AND_NOT_ASSIGNED =
			"a final variable was declared and not assigned";

	//all the types singletons

	private CharPattern charPattern = CharPattern.getMyInstance();

	private BooleanPattern booleanPattern = BooleanPattern.getMyInstance();

	private DoublePattern doublePattern = DoublePattern.getMyInstance();

	private StringPattern stringPattern = StringPattern.getMyInstance();

	private IntPattern intPattern = IntPattern.getMyInstance();


	private final String myName;
	private String myValue;
	private final String myType;
	private boolean isAssigned;
	private boolean isFinal;
	private Matcher myMatcher;


	/**
	 * Constructor for variable object, used for declaring a variable with a value.
	 *
	 * @param name       String - name of variable.
	 * @param type       String - name of type.
	 * @param isAssigned Boolean - true is a value was given to variable. false otherwise.
	 */
	public Variable(String name, String type, String value, boolean isAssigned, boolean isFinal)
			throws ExceptionIncompatibleType, ExceptionVariableDeclarationAndAssignment {
		this.myName = name;
		this.myType = type;
		this.myValue = value;
		this.isAssigned = isAssigned;
		this.isFinal = isFinal;
		if (myValue!=null) {
			matchType(type, value);
		}
		if((isFinal)&&(!isAssigned)){
			throw  new ExceptionVariableDeclarationAndAssignment
					(A_FINAL_VARIABLE_WAS_DECLARED_AND_NOT_ASSIGNED);
		}
	}

	/**
	 * Returns name of variable.
	 *
	 * @return this.myName
	 */
	public String getMyName() {
		return myName;
	}

	/**
	 * Returns name of variable.
	 *
	 * @return this.myValue
	 */
	public String getMyValue() {
		return myValue;
	}


	/**
	 * Returns type of variable.
	 *
	 * @return this.my type
	 */
	public String getMyType() {
		return myType;
	}

	/**
	 * Returns assignment status.
	 *
	 * @return boolean value.
	 */
	public boolean assignmentStatus() {
		return isAssigned;
	}

	/**
	 * Returns assignment status.
	 *
	 * @return boolean value.
	 */
	public boolean isItFinal() {
		return isFinal;
	}

	/**
	 * Change status of isAssigned to true.
	 */
	public void setValue(String value) {
		isAssigned = true;
		myValue = value;
	}

	/*
	 * Verifies that the assignment is legal by the types.
	 * @param myType
	 * @param myValue
	 * @throws ExceptionIncompatibleType
	 */
	private void matchType(String myType, String myValue) throws ExceptionIncompatibleType {
		if (myType.equals(intPattern.getMyTypeName())) {
			myMatcher = Pattern.compile(intPattern.getPureAssignment()).matcher(myValue);
			if (!myMatcher.matches()) {
				throw new ExceptionIncompatibleType();
			}
		}
		else if (myType.equals(doublePattern.getMyTypeName())) {
			myMatcher = Pattern.compile(doublePattern.getPureAssignment()).matcher(myValue);
			if (!myMatcher.matches()) {
				throw new ExceptionIncompatibleType();
			}
		}
		else if (myType.equals(booleanPattern.getMyTypeName())) {
			myMatcher = Pattern.compile(booleanPattern.getPureAssignment()).matcher(myValue);
			if (!myMatcher.matches()) {
				throw new ExceptionIncompatibleType();
			}
		}
		else if (myType.equals(stringPattern.getMyTypeName())) {
			myMatcher = Pattern.compile(stringPattern.getPureAssignment()).matcher(myValue);
			if (!myMatcher.matches()) {
				throw new ExceptionIncompatibleType();
			}
		}
		else if (myType.equals(charPattern.getMyTypeName())) {
			myMatcher = Pattern.compile(charPattern.getPureAssignment()).matcher(myValue);
			if (!myMatcher.matches()) {
				throw new ExceptionIncompatibleType();
			}
		}

	}
}


