package oop.ex6.Scopes.Parsers;
import oop.ex6.Line;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.*;
import oop.ex6.Scopes.Parsers.LineMannagers.LineParser;

import java.util.*;

/**
 * Class for Scope parser, which parser through lines already set in the given scopes data.
 * @author shaharna13
 * @author dori203
 */
public class ScopeParser {

	// Error massage
	private static final String PARENTHESES_IN_OUTER_SCOPE = "Parentheses in outer scope";
	private static final String IF_WHILE_BLOCK_INSIDE_OF_THE_OUTER_SCOPE_IS_NOT_ALLOWED = "if/while block inside of the outerScope is not allowed";

	private Scope myScope;
	private LineParser myLineParser;
	private Scope curScope;

	/**
	 * Constructor
	 * @param scope the scope we are handling
	 */
	public ScopeParser(Scope scope){
		myScope = scope;
		myLineParser = LineParser.getInstance();
		curScope = myScope;
	}

	/**
	 * This method send each line of data to line parser, with the curScope field.
	 * @throws RegexException textual exception
	 * @throws LogicException logical exception
	 */
	public void parseLines() throws RegexException, LogicException{ //todo change exception type.
		for (Line line : myScope.myLines){
			myLineParser.parseLine(line, curScope, myScope);
		}
	}

	/**
	 * Method for starting a new blockScope for a while/if block. Activated by LineParser, when a new block
	 * is identified.
	 * @param params ArrayList of parameters, each is a suppousedly assigned variable..
	 * @param myScope the current scope
	 * @throws RegexException textual exception
	 * @throws LogicException logical exception
	 */
	public void startBlock(ArrayList<String> params, Scope myScope) throws RegexException, LogicException{ //TODO change exception.
		if (myScope.getMyParent() == null){
			throw new RegexException(IF_WHILE_BLOCK_INSIDE_OF_THE_OUTER_SCOPE_IS_NOT_ALLOWED);
		}
		curScope = new BlockScope(myScope, params, myScope.getMyOuter()); //create an anonymous blockScope.
	}

	/**
	 * This method closes a scope, and changes curScope to the current scope's parent.
	 * @throws RegexException textual exception
	 */
	public void closeBlock() throws RegexException{
		curScope = curScope.getMyParent();
		if (curScope.getMyParent() == null) // one paranthes missing.
			throw new RegexException(PARENTHESES_IN_OUTER_SCOPE);
	}
}
