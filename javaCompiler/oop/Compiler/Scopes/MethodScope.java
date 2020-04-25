package oop.ex6.Scopes;

import oop.ex6.Regex.Patterns.*;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.Parsers.LineMannagers.LineParser;
import oop.ex6.Scopes.Parsers.ScopeParser;
import oop.ex6.Variable;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for Method Scope, which is responsible for method scope
 * @author shaharna13
 * @author dori203
 * @see Scope
 */

public class MethodScope extends Scope {


	//error messages
	private static final String TWO_PARAMS_WITH_THE_SAME_NAME_IN_THE_FUNCTION_SIGNATURE =
			"two params with the same name in the function signature";
	private static final String COMMA_SPLIT = ",";

	// general line pattern
	private Pattern getParamPattern = GeneralPattern.getMyInstance().getNoNamePattern();

	// method string regex
	private String methodParam = "(\\s*final)?\\s*(" +
			LineParser.getInstance().allTypesOptions() + ")" + DeclarationPatterns.getNamePattern();
	private Pattern paramPattern = Pattern.compile(methodParam);


	/*
	 * My parameters as a linked list of Strings..
	 */
	private LinkedList<Variable> myParams = new LinkedList<Variable>();
	/*
	 * My method name.
	 */
	private String myName;
	// the outer scope
	private OuterScope outerScope;

	/**
	 * * Constructor
	 * @param parentScope the parent scope
	 * @param name the method name
	 * @param paramsString the method params as they are written in its signature.
	 * @throws ExceptionInvalidParameters invalid parameters was inserted
	 * @throws ExceptionIncompatibleType incompatible type in the parameters
	 * @throws ExceptionVariableDeclarationAndAssignment an error with the assignment or declaration
	 * @throws RegexException textual exception
	 */
	public MethodScope(OuterScope parentScope, String name, String paramsString)
			throws ExceptionInvalidParameters,ExceptionIncompatibleType,
			ExceptionVariableDeclarationAndAssignment, RegexException {
		myParent = parentScope;
		this.outerScope = parentScope;
		myName = name;
		myParser = new ScopeParser(this);
		setParameters(paramsString);
		for (Variable param : myParams) {
			myVariables.add(param);
		}
	}

	/*
	 * GETTER - method name
	 *
	 * @return myName
	 */
	String getMyName() {
		return myName;
	}


	/*
	 * This method extracts the data from the String representation of the parameters, the myParams.
	 * @param parameters String representation of the parameters.
	 * @throws RegexException textual exception
	 * @throws ExceptionInvalidParameters logical exception
	 * @throws ExceptionVariableDeclarationAndAssignment logical exception
	 * @throws ExceptionIncompatibleType logical exception
	 */
	private void setParameters(String parameters) throws RegexException,
			ExceptionIncompatibleType,ExceptionInvalidParameters , ExceptionVariableDeclarationAndAssignment{
		String[] paramArray = parameters.split(COMMA_SPLIT);
		if ((paramArray.length == 1) && (paramArray[0].equals(""))) {
			return;
		}
		for (String param : paramArray) {
			Matcher paramMatcher = paramPattern.matcher(param);
			if (!paramMatcher.matches()) {
				throw new RegexException();
			}
			String isFinal = paramMatcher.group(1);
			String paramType = paramMatcher.group(2);
			String paramName = paramMatcher.group(3);
			if (isFinal == null) { //not a final param.
				myParams.add(new Variable(paramName, paramType, null, true, false));
			} else {
				myParams.add(new Variable(paramName, paramType, null, true, true));
			}
		}
		verifyMultiplications();
	}



	/*
	 * This function checks whether or not a given params are matching its declaration params.
	 *
	 * @param givenParams name of each param.
	 * @throws ExceptionInvalidParameters
	 */
	void areParamsMatching(String[] givenParams, Scope curScope) throws ExceptionInvalidParameters,
			ExceptionIncompatibleType {
		Matcher valueMatcher;
		String typeOfVariable = null;
		if (((givenParams == null)&&(myParams.size()==0))){
			return;
		}
		else {
			if((givenParams==null)||(givenParams.length!=myParams.size())){
				throw new ExceptionInvalidParameters();
			}
		}
		for (int i = 0; i < (myParams.size()); i++) {
			valueMatcher = getParamPattern.matcher(givenParams[i]);
			if (valueMatcher.matches()) {//if you are actually a value (not variable)
				if (valueMatcher.group(2) != null) {
					typeOfVariable = stringPattern.getMyTypeName();
				} else if (valueMatcher.group(3) != null) {//int
					typeOfVariable = intPattern.getMyTypeName();
				} else if (valueMatcher.group(4) != null) {
					typeOfVariable = doublePattern.getMyTypeName();
				} else if (valueMatcher.group(5) != null) {
					typeOfVariable = booleanPattern.getMyTypeName();
				} else if (valueMatcher.group(8) != null) {
					typeOfVariable = charPattern.getMyTypeName();
				}
			}
			else {// yoa are a reference to other variable
				Variable actualVariable = findVariable(givenParams[i], curScope);
				if ((actualVariable == null)||(actualVariable.getMyValue()==null)) {
					throw new ExceptionInvalidParameters();
				}
				typeOfVariable = actualVariable.getMyType();
			}
			paramsCompatible(typeOfVariable, myParams.get(i).getMyType());
		}
	}

	@Override
	public OuterScope getMyOuter() {
		return outerScope;
	}

	/*
	 * this function verify that there are no two variables declared in the method
	 * signature with the same name
	 * @throws ExceptionInvalidParameters
	 */
	private void verifyMultiplications() throws ExceptionInvalidParameters {
		for (Variable var : myParams) {
			for (Variable secondVar : myParams) {
				if ((var.getMyName().equals(secondVar.getMyName())&& var!=secondVar)){
					throw new ExceptionInvalidParameters
							(TWO_PARAMS_WITH_THE_SAME_NAME_IN_THE_FUNCTION_SIGNATURE);
				}
			}
		}
	}
}