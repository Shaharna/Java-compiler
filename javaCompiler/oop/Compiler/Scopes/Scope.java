package oop.ex6.Scopes;

import oop.ex6.Line;
import oop.ex6.Regex.Patterns.*;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.Parsers.ScopeParser;
import oop.ex6.Variable;
import java.util.LinkedList;

/**
 * Abstract Class for Scope, which describes a scope in the sjava file.
 * @author shaharna13
 * @author dori203
 */

public abstract class Scope {

	//error messages
	private static final String METHOD_DOES_NOT_EXIST = "method does not exist";
	private static final String METHODS_CAN_ONLY_BE_CALLED_INSIDE_ANOTHER_METHOD =
			"Methods can only be called inside another method!";
	private static final String VARIABLE_DECLARED_AS_FINAL =
			"variable declared as final and cant be assign";
	private static final String THE_VARIABLE_YOU_ASSIGNED_TO_DOES_NOT_EXIST =
			"the variable you assigned to does not exist";


	//all the types singleton

	 BooleanPattern booleanPattern = BooleanPattern.getMyInstance();

	 DoublePattern doublePattern = DoublePattern.getMyInstance();

	 StringPattern stringPattern = StringPattern.getMyInstance();

	 IntPattern intPattern = IntPattern.getMyInstance();

	 CharPattern charPattern = CharPattern.getMyInstance();


	/*My variable linked list.*/
	LinkedList<Variable> myVariables = new LinkedList<Variable>();
	/**My lines.**/
	public LinkedList<Line> myLines = new LinkedList<Line>();
	/*My parent.*/
	 Scope myParent;
	/*My ScopeParser.*/
	 ScopeParser myParser;




	public void parseMe() throws RegexException, LogicException{
		myParser.parseLines();
	}

	/**
	 * Getter
	 * @return myVariables
	 */
	public LinkedList<Variable> getMyVariables() {
		return myVariables;
	}

	/**
	 * This function adds a new variable to the scopes list of variables if it hadn't been declared yet.
	 * Otherwise it will raise an exception
	 * @param name  the variable name.
	 * @param type the variable type.
	 * @param isAssigned boolean indicating if the variable has been assigned.
	 * @throws LogicException a double declaration exception.
	 */
	public void declareVariable(String name, String type,String value, boolean isAssigned, boolean isFinal)
			throws LogicException {
		isDeclared(name);
		Variable newVar = new Variable(name, type, value, isAssigned, isFinal);
		myVariables.add(newVar);
	}

	/*
	 * This function checks whether or not the variable just declared is already declared in the scope.
	 * @param newVar the variable assigned to.
	 * @throws ExceptionVariableDeclarationAndAssignment
	 */
	private void isDeclared(String newVar) throws ExceptionVariableDeclarationAndAssignment {
		for (Variable var: myVariables)
		{
			if (var.getMyName().equals(newVar)){
				throw new ExceptionVariableDeclarationAndAssignment();
			}
		}
	}

	/**
	 * this function checks whether or not a variable exists in the code. if not it will return null.
	 * @param variableName the variable
	 * @return null if no match was found
	 */
	public Variable findVariable(String variableName, Scope curScope) {
		Scope currentScope = curScope;
		while (currentScope != null) {
			for (Variable var : currentScope.myVariables) {
				if (var.getMyName().equals(variableName)) {
					return var;
				}
			}
			currentScope = currentScope.myParent;
		}
		return null;
	}


	/**
	 * This function checks whether or not the assignment is legal
	 * @param name the variable name.
	 * @param assignmentType the variable type.
	 * @throws LogicException Incompatible Types exception.
	 */
	public void assign (String name, String assignmentType, String value) throws LogicException {
		boolean flag = false;
		Scope currentScope = this;
		Scope originalScope = this;
		while (currentScope!=null){
			for (Variable var: currentScope.myVariables)
			{
				if (var.getMyName().equals(name)){
					paramsCompatible(assignmentType,var.getMyType());
					if(var.isItFinal()){
						throw new ExceptionVariableDeclarationAndAssignment(VARIABLE_DECLARED_AS_FINAL);
					}
					else{
						flag = true;
						if (currentScope == originalScope) {
							var.setValue(value);
						}
						else{
							Variable varCopy = dupliace_variable(var);
							varCopy.setValue(value);
							originalScope.myVariables.add(varCopy);
						}
					}
			}
			}
			currentScope = currentScope.myParent;
		}
		if (!flag){
			throw new ExceptionVariableDeclarationAndAssignment(THE_VARIABLE_YOU_ASSIGNED_TO_DOES_NOT_EXIST);
		}
	}

	/**
	 * This function returns the method matching the name received if exists.
	 * Otherwise it will raise an exception raised if the method is never declared in the code.
	 * @param methodName the method name
	 * @return the method matching this name if exists.
	 * @throws LogicException an exception raised if the method is never declared in the code.
	 */
	private MethodScope getMethodByName(String methodName, OuterScope outerScope) throws LogicException{
		for (MethodScope method:outerScope.myMethods){
			if(methodName.equals(method.getMyName())){
				return method;
			}
		}
		throw new LogicException(METHOD_DOES_NOT_EXIST);
	}

	/**
	 * This function matches the sent params to the params the function needs.
	 * @param methodName
	 * @param sentParams
	 * @throws LogicException
	 * @throws LogicException
	 */
	public void methodCall(String methodName, String[] sentParams)throws
			LogicException{
		if (myParent==null){
			throw new LogicException(METHODS_CAN_ONLY_BE_CALLED_INSIDE_ANOTHER_METHOD);
		}
		MethodScope method = getMethodByName(methodName,this.getMyOuter());
		method.areParamsMatching(sentParams, this);
	}


	public abstract OuterScope getMyOuter();


	/**
	 * GETTER - my parent
	 * @return myParent
	 */
	public Scope getMyParent() {return myParent;}

	/**
	 * GETTER - my parser.
	 * @return myParser.
	 */
	public ScopeParser getMyParser() {return myParser;}

	/*
	 * Checks whether the variable given has the compatible type as the variable expected.
	 * @param paramType The type of the parameter given to the assignment, as String.
	 * @param expectedVariable The Variable object to assign to
	 * @throws Exception
	 */
	 public void paramsCompatible(String paramType, String expectedType) throws ExceptionIncompatibleType{
		if (!expectedType.equals(paramType)){
			if (expectedType.equals(booleanPattern.getMyTypeName())){
				if (!((paramType.equals(intPattern.getMyTypeName()))||
						(paramType.equals(doublePattern.getMyTypeName())))){
					throw new ExceptionIncompatibleType();
				}
			}
			else if (expectedType.equals(doublePattern.getMyTypeName())){
				if (!(paramType.equals(intPattern.getMyTypeName()))){
					throw new ExceptionIncompatibleType();
				}
			}
			else {
				throw new ExceptionIncompatibleType();
		}
		}
	}

	/*Creates another instance of a variable.*/
	private Variable dupliace_variable(Variable variable) throws LogicException{
		return new Variable(variable.getMyName(),variable.getMyType(),variable.getMyValue(),
			   variable.assignmentStatus(),variable.isItFinal());
	}
}
