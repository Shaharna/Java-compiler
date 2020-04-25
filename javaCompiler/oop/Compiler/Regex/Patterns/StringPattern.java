package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;


/**
 * Pattern class for String Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class StringPattern implements DeclarationPatterns{

	private static StringPattern myInstance= new StringPattern();

	private static final String name = "String";

	private static final String stringPattern = "\\s*(String)";

	static final String stringPureAssignment = "(\\s*[\"].*[\"]\\s*)";

	static final String stringAssignmentName = "(\\s*[\"].*[\"]\\s*)|"+namePattern;


	//private constructor
	private StringPattern(){
	}
	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static StringPattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(stringPattern);
	}


	@Override
	public String getMyTypeName() {
		return name;
	}

	@Override
	public String getPureAssignment() {
		return stringPureAssignment;
	}
}
