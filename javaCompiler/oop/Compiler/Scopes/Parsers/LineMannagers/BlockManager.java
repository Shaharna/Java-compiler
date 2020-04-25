package oop.ex6.Scopes.Parsers.LineMannagers;
import oop.ex6.Line;
import oop.ex6.Regex.Patterns.OpenBlockPattern;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.LogicException;
import oop.ex6.Scopes.Scope;

import java.util.ArrayList;
import java.util.regex.Matcher;


/**
 * a class handel block beginning in the sjava file
 * @author shaharna13 dori203
 * @see oop.ex6.Scopes.Parsers.LineMannagers.LineParser
 */

 class BlockManager {

 	//Error messages
	private static final String NOT_A_VALID_OPENING_IF_WHILE_LINE = "not a valid opening if/while line";
	private static final String PARENTHESES_IN_OUTER_SCOPE = "Parentheses in outer scope";
	private OpenBlockPattern openBlockPattern  = OpenBlockPattern.getMyInstance();

	private Matcher myMatcher;

	 BlockManager(Matcher matcher){
		myMatcher = matcher;
	}

	/*
	 * This function handle block beginning
	 * @param line
	 * @param myScope
	 * @param curScope
	 * @throws RegexException
	 * @throws LogicException
	 */
	 void blockStarter(Line line, Scope myScope, Scope curScope) throws RegexException, LogicException {
		myMatcher = OpenBlockPattern.openBlockCompletePattern.matcher(line.getContent());
		if (!myMatcher.matches()){
			throw new RegexException(NOT_A_VALID_OPENING_IF_WHILE_LINE);
		}
		handleBlock(line, myScope, curScope);
	}

	 void closingBlock(Scope myScope, Scope curScope) throws LogicException, RegexException {
		if (curScope.getMyParent() == null){
			throw new LogicException(PARENTHESES_IN_OUTER_SCOPE);
		}
		if (myScope.getMyParent().getMyParent()!=null){
			curScope.getMyParser().closeBlock();
		}
	}


	/*
	 * Private method for handling lines of opening blocks.*
	 * @param line
	 * @param myScope
	 * @param parsingScope
	 * @throws RegexException
	 * @throws LogicException
	 */
	private void handleBlock(Line line, Scope myScope, Scope parsingScope) throws
			RegexException,LogicException {
		Matcher blockMatcher = openBlockPattern.getOpenBlockPattern().matcher(line.getContent());
		if (blockMatcher.matches()){
			String paramString = blockMatcher.group(3);
			paramString = paramString.replaceAll("\\(|\\)","");
			String[] paramsArray = paramString.split(openBlockPattern.getParamSeparator());
			ArrayList<String> params = new ArrayList<String>();
			for (String param: paramsArray) { //for each parameter, check if it's legit.
				param = param.trim();
				Matcher paramMatcher = openBlockPattern.getParamsPattern().matcher(param);
				if (!paramMatcher.matches()){
					throw new RegexException();
				}
				if(paramMatcher.group(4)!=null){
					params.add(paramMatcher.group(4));
				}
			}
			parsingScope.getMyParser().startBlock(params, myScope);
		}
	}

}
