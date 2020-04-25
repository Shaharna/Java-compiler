package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern class for Char Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */


public class CharPattern implements DeclarationPatterns {

	private static CharPattern myInstance= new CharPattern();

	private static final String name = "char";

	private static final String charPattern = "\\s*(char)";

	static final String charPureAssignment = "(\\s*['].[']\\s*)";

	static final String charAssignmentName = "(\\s*['].[']\\s*)|"+namePattern;


	//private constructor
	private CharPattern(){
	}
	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static CharPattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(charPattern);
	}

	@Override
	public String getMyTypeName() {
		return name;
	}

	@Override
	public String getPureAssignment() {
		return charPureAssignment;
	}
}

