package oop.ex6.Scopes;

import oop.ex6.Regex.Patterns.*;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.Parsers.ScopeParser;
import oop.ex6.Variable;
import java.util.*;

/**
 * Class for Block Scope , which is responsible for the if and while blocks
 * @author shaharna13
 * @author dori203
 * @see Scope
 */

public class BlockScope extends Scope {


	private static final String VARIABLE_TYPE_IS_NOT_OK = "variable type is not ok";
	private static final String VARIABLE_DOES_NOT_EXIST = "variable does not exist";
	private static final String VARIABLE_USED_IN_IF_WHILE_BLOCK_IS_NOT_ASSIGNED =
			"Variable used in if/while block is not assigned.";

	//*************************** Patterns Instances**************************************//

	private BooleanPattern booleanPattern = BooleanPattern.getMyInstance();

	private DoublePattern doublePattern = DoublePattern.getMyInstance();

	private IntPattern intPattern = IntPattern.getMyInstance();


	/**My parameters as a linked list of Strings..**/
	private LinkedList<String> myParams = new LinkedList<String>();
	private OuterScope outerScope;

	/**
	 * Constructor of the BlockScope class.
	 * @param parentScope my parent scope.
	 * @throws RegexException,LogicException both logical and regex exception.
	 */
	public BlockScope(Scope parentScope, ArrayList<String> params, OuterScope outerScope)
			throws RegexException,LogicException{
		this.outerScope = outerScope;
		myParent = parentScope;
		setParameters(params);
		myParser = null;
		myLines = null;
	}


	/*
	 * This method verifies that the given parameters for the if/while block are initaiated.
	 * @param paramsArray ArrayList of variable names.
	 */
	private void setParameters(ArrayList<String> paramsArray) throws LogicException{
		if (paramsArray.size() > 0) {
			for (String param : paramsArray) {
				Variable foundVar = findVariable(param, this);
				if (foundVar == null) {
					throw new LogicException(VARIABLE_DOES_NOT_EXIST);
				}
				if (!foundVar.assignmentStatus()){
					throw new LogicException(VARIABLE_USED_IN_IF_WHILE_BLOCK_IS_NOT_ASSIGNED);
				}
				String varType = foundVar.getMyType();
				if ((!varType.equals(booleanPattern.getMyTypeName())) &&
						(!varType.equals(intPattern.getMyTypeName())) &&
						(!varType.equals(doublePattern.getMyTypeName()))) {//not int, double,and boolean
					throw new LogicException(VARIABLE_TYPE_IS_NOT_OK);
				}
			}
		}
	}

	/**
	 * Override of the parseMe method.
	 * @throws RegexException only textual exception.
	 */
	@Override
	public void parseMe() throws RegexException { ;
	}

	@Override
	public OuterScope getMyOuter() {
		return outerScope;
	}


}
