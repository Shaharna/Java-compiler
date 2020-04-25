package oop.ex6.main;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.Parsers.FileParser;
import oop.ex6.Scopes.*;

import java.io.*;

/**
 * the main class running the code
 */
public class Sjavac {

	/* ERRORS*/
	private static final String NO_ERRORS = "0";
	private static final String IO_ERROR = "2";
	private static final String SYNTAX_LOGIC_ERROR = "1";

	/**
	 * the main method for the Sjavac program.
	 * @param args - pathName of Sjavac file to be processed.
	 */
	public static void main(String[] args){
		try {
			if (args.length != 1){
				throw new IOException();
			}
			String filePath = args[0];
			FileParser fileParser = new FileParser(filePath);
			OuterScope outerScope = fileParser.parse();
			outerScope.parseMe();
			for( MethodScope method: outerScope.myMethods)
				method.parseMe();
			System.out.println(NO_ERRORS);
		}
		catch (IOException io){
			System.out.println(IO_ERROR);
		}
		catch (LogicException|RegexException e){
			System.out.println(SYNTAX_LOGIC_ERROR);
			System.err.print(e.getMessage());
		}
	}

}
