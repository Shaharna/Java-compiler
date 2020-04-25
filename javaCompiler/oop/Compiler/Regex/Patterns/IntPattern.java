package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern class for int Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class IntPattern implements DeclarationPatterns{

	private static IntPattern myInstance= new IntPattern();

	private static final String name = "int";

	private static final String intPattern = "\\s*(int)";

	static final String intPureAssignment = "(\\s*-?\\d+\\s*)";

	static final String intAssignmentName = "("+intPureAssignment+")|"+namePattern;


	//private constructor
	private IntPattern(){
	}

	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static IntPattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(intPattern);
	}

	@Override
	public String getMyTypeName() {
		return name;
	}
	@Override
	public String getPureAssignment() {
		return intPureAssignment;
	}
}
