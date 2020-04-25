package oop.ex6.Scopes.Parsers.LineMannagers;

import oop.ex6.Regex.Patterns.DeclarationPatterns;
import oop.ex6.Regex.Patterns.GeneralPattern;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.LogicException;
import oop.ex6.Scopes.Scope;
import oop.ex6.Variable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * a class handel declaration and assignment of variables
 * @author shaharna13 dori203
 * @see oop.ex6.Scopes.Parsers.LineMannagers.LineParser
 */



class DeclareAndAssign {

	private Matcher myMatcher;

	private LineParser myLineParser = LineParser.getInstance();

	DeclareAndAssign(Matcher matcher){
		this.myMatcher = matcher;
	}

	private static final String VARIABLE_DOES_NOT_EXIST = "variable does not exist";

	private GeneralPattern generalPattern = GeneralPattern.getMyInstance();



	/*
	 * This function handle a variable assignment (without declaration)
	 * @param myScope
	 * @param assignLine
	 * @throws Exception
	 */
	void handleAssignment(Scope myScope, String assignLine) throws RegexException,LogicException {
		String name, toAssign;
		Variable toAssignVar;
		Matcher typeMatcher = generalPattern.getMyAssignmentPattern().matcher(assignLine);
		if (!typeMatcher.matches()){
			throw new RegexException();
		}
		if(typeMatcher.group(2)!=null) {
			name = typeMatcher.group(2);
			for (int i = 4; i < 9; i++) { //TODO notice it starts a 1.
				if (typeMatcher.group(i) != null) {
					String valueType = myLineParser.allTypes[i - 4];
					myScope.assign(name, valueType, myMatcher.group(22));
					break;
				}
			}
			return;
		}
		assignLine = assignLine.replace(";", "");
		Pattern namePattern = Pattern.compile(DeclarationPatterns.getNamePattern() +
				"\\s*=" + DeclarationPatterns.getNamePattern());
		typeMatcher = namePattern.matcher(assignLine);
		if (typeMatcher.matches()) {
			name = typeMatcher.group(1);
			toAssign = typeMatcher.group(2);
			toAssignVar = myScope.findVariable(toAssign, myScope);
			if (toAssignVar == null) {
				throw new LogicException(VARIABLE_DOES_NOT_EXIST);
			}
			myScope.assign(name, toAssignVar.getMyType(), toAssignVar.getMyValue());
			return;
		}
	}

	/*
	 * This function handel a variable declaration.
	 * @param myScope the current scope.
	 * @throws Exception
	 */
	void handleDeclaration(Scope myScope) throws LogicException, RegexException {
		String declarations = myMatcher.group(3);
		boolean isFinal = (myMatcher.group(1)!=null);
		String type = myMatcher.group(2);
		if (myMatcher.group(21)==null){
			throw new RegexException();
		}
		DeclarationManager(myScope, declarations, isFinal, type);
	}

	/*
	 * This function manages the declaration dividing.
	 * @param myScope
	 * @param declarations
	 * @param isFinal
	 * @param type
	 * @throws LogicException
	 * @throws RegexException
	 */
	private void DeclarationManager(Scope myScope, String declarations, boolean isFinal, String type)
			throws LogicException, RegexException {
		Pattern declaration =
				Pattern.compile(DeclarationPatterns.getNamePattern()+"(=\\s*"+
						generalPattern.getNoNamePattern()+")*");
		String[] splitParams = declarations.split(",");
		for (int i = 0; i<splitParams.length; i++){
			if (i == splitParams.length - 1) {
				splitParams[i] = splitParams[i].replace(";", "");
			}
			if(!splitParams[i].contains("=")) {
				if(Pattern.compile(DeclarationPatterns.getNamePattern()).matcher(splitParams[i]).matches()) {
					myScope.declareVariable(splitParams[i].trim(), type, null, false, isFinal);
				}
				else throw new RegexException();
			}
			else {
				declarationVerification(myScope, isFinal, type, declaration, splitParams[i]);
			}
		}
	}

	/*
	 * This function handel a declaration line.
	 * @param myScope the current scope
	 * @param isFinal is the variable is declared final
	 * @param type the variable's type
	 * @param declaration the declaration pattern
	 * @param splitParams an array holding all the params spited by a comma
	 * @throws Exception
	 */
	private void declarationVerification(Scope myScope, boolean isFinal, String type, Pattern declaration,
										 String splitParam) throws LogicException, RegexException {
		Matcher declareMatcher;
		declareMatcher = declaration.matcher(splitParam);
		if(declareMatcher.matches()) {
			valueDeclaration(myScope, isFinal, type, declareMatcher);
			return;
		}
		else {
			Pattern namePattern = Pattern.compile(DeclarationPatterns.getNamePattern()+
					"\\s*="+DeclarationPatterns.getNamePattern());
			declareMatcher = namePattern.matcher(splitParam);
			if (declareMatcher.matches()) {
				variableDeclaration(myScope, isFinal, type, declareMatcher);
				return;
			}
		}
		throw new RegexException();
	}


	/*
	 * This function handle a variable assignment in the declaration line.
	 * @param myScope
	 * @param isFinal
	 * @param type
	 * @param declareMatcher
	 * @throws LogicException
	 */
	private void variableDeclaration(Scope myScope, boolean isFinal, String type, Matcher declareMatcher)
			throws LogicException {
		String name;
		String toAssign;
		Variable toAssignVar;
		name = declareMatcher.group(1);
		toAssign = declareMatcher.group(2);
		toAssignVar = myScope.findVariable(toAssign, myScope);
		if ((toAssignVar == null)|| (toAssignVar.assignmentStatus()==false)){
			throw new LogicException(VARIABLE_DOES_NOT_EXIST);
		}
		myScope.paramsCompatible(toAssignVar.getMyType(), type);
		myScope.declareVariable(name, type,toAssignVar.getMyValue(),toAssignVar.assignmentStatus(),isFinal);
	}


	/*
	 * This method handle a value and not variable assigned into the declaration
	 * @param myScope
	 * @param isFinal
	 * @param type
	 * @param declareMatcher
	 * @throws RegexException
	 * @throws LogicException
	 */
	private void valueDeclaration(Scope myScope, boolean isFinal, String type, Matcher declareMatcher)
			throws RegexException, LogicException {
		String name;
		String value;
		boolean isAssigned;
		name = declareMatcher.group(1);
		value = declareMatcher.group(3);
		if (declareMatcher.group(2) != null) {
			isAssigned = true;
		} else {
			throw new RegexException();
		}
		myScope.declareVariable(name, type, value, isAssigned, isFinal);
	}
}
