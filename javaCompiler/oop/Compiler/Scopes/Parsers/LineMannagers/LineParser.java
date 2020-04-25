package oop.ex6.Scopes.Parsers.LineMannagers;

import oop.ex6.Line;
import oop.ex6.Regex.Patterns.*;
import oop.ex6.Scopes.LogicException;
import oop.ex6.Scopes.Scope;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oop.ex6.Regex.RegexException;

/**
 * a class parsering each and every line of the code and sending it to the right process to be handled
 * @author shaharna13 dori203
 * @see DeclareAndAssign
 * @see BlockManager
 */


public class LineParser {

	//CONSTANTS

	private static final String COMMENT = "//";
	private static final String PARENTHESES = "}";
	private static final String INCORRECT_LINE = "incorrect line";
	private static final String IF_VALUE = "if";
	private static final String WHILE_VALUE = "while";

	//all the types singleton
	//*************************** Patterns Instances**************************************//

	private BooleanPattern booleanPattern = BooleanPattern.getMyInstance();

	private GeneralPattern generalPattern = GeneralPattern.getMyInstance();

	private DoublePattern doublePattern = DoublePattern.getMyInstance();

	private StringPattern stringPattern = StringPattern.getMyInstance();

	private IntPattern intPattern = IntPattern.getMyInstance();

	private FinalPattern finalPattern  = FinalPattern.getMyInstance();

	private CharPattern charPattern = CharPattern.getMyInstance();



	/**
	 * a list holding all the types the declaration holds,
	 * it enables an easy way to add new patten type to the code
	 */
	 final String[] allTypes = {stringPattern.getMyTypeName(),doublePattern.getMyTypeName(),
			intPattern.getMyTypeName(), booleanPattern.getMyTypeName(),charPattern.getMyTypeName()};


	// a matcher
	private Matcher myMatcher;

	private DeclareAndAssign declareAndAssignManager;
	private BlockManager blockManager;


	/**Singleton instance**/
	private static LineParser instance = new LineParser();
	/**Private constructor for Singleton purposes.**/
	private LineParser() {
	}

	/**
	 * Returns instance of LineParser.
	 * @return instance of LineParser.
	 */
	public static LineParser getInstance() {
		return instance;
	}


	/**
	 * This function parse one line in the code.
	 * @param line the line object which holds the content of the line and its index;
	 * @throws RegexException incorrect line exception.
	 */
	public void parseLine (Line line, Scope myScope, Scope parsingScope)
			throws RegexException,LogicException {
		doesMatch(line,generalPattern.getMyGeneralPattern(allTypesOptions()));
		String startWith = myMatcher.group(1);
		int counter=2;
		while ((startWith==null )&& counter<myMatcher.groupCount()){
			startWith = myMatcher.group(counter);
			counter++;
		}
		if(startWith!=null){
			findPattern(startWith, line, myScope, parsingScope);
		}
}

	/*
	 * Throws an exception if the line does not match the general pattern. Meaning the structure of the
	 * line is not one of the given options.
	 * @param line the line to parse
	 * @param lineNum the line number
	 * @param pattern the pattern to match
	 * @throws RegexException an exception raised if the line does not match any pattern.
	 */
	private void doesMatch(Line line, Pattern pattern) throws RegexException {
		myMatcher = pattern.matcher(line.getContent());
		if(!myMatcher.matches()){
			throw new RegexException(INCORRECT_LINE +line.getIndex());
		}
	}

	/*
	 * This function finds the correct pattern according to the beginning of the line.
	 * @param startsWith
	 * @return
	 */
	private void findPattern (String startsWith,Line line,  Scope myScope, Scope curScope)
			throws RegexException, LogicException {
		declareAndAssignManager= new DeclareAndAssign(myMatcher);
		blockManager = new BlockManager(myMatcher);
		if ((Arrays.asList(allTypes).contains(startsWith))||startsWith.equals(finalPattern.getMyTypeName())){ //TODO checks declarations
			declareAndAssignManager.handleDeclaration(myScope);
			return;
		}
		else if (generalPattern.getReturnPattern().matcher(startsWith).matches()){
			if(myScope.getMyParent()!=null){
				return;
			}
			else throw new RegexException();
		}
		else if ((startsWith.equals(COMMENT))){
			return;
		}
		else if ((startsWith.equals(PARENTHESES))){
			blockManager.closingBlock(myScope, curScope);
			return;
		}
		else if ((startsWith.equals(IF_VALUE)) || (startsWith.equals(WHILE_VALUE))) {
			//a new if/while block is created.
			blockManager.blockStarter(line, myScope, curScope);
			return;
		}
		else{
			if ((myMatcher.group(21)!=null)||(myMatcher.group(36)!=null)){ //assignment.
				if ((myMatcher.group(21)!=null)){
					if (myMatcher.group(21).contains("=")) {
						declareAndAssignManager.handleAssignment(myScope, myMatcher.group(0));
						return;
					}
				}
				else if (myMatcher.group(37) != null) {
					if(myMatcher.group(38)!=null){
						String[] params = myMatcher.group(38).split(",");
						myScope.methodCall(myMatcher.group(36), params);
						return;
					}
					else {
						myScope.methodCall(myMatcher.group(36),null);
						return;
					}
				}
			}
			}
			throw new RegexException();
	}
	/**
	 * A function returning a regex expression holding all the types available in the sjava doc.
	 * @return allTypesOptions separated with "|"
	 */
	public String allTypesOptions(){
		String allTypesOptions= allTypes[0];
		for(int i=1; i<allTypes.length; i++ ){
			allTypesOptions+= "|"+allTypes[i];
		}
		return allTypesOptions;
	}
}

