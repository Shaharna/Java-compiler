package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;


/**
 * Pattern class for Double Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class DoublePattern implements DeclarationPatterns {

	private static DoublePattern myInstance= new DoublePattern();

	private static final String name = "double";

	private static final String doublePattern = "(double)";

	static final String doublePureAssignment = "(\\s*[-]?\\d+[.]?+\\d*\\s*)";

	static final String doubleAssignmentName = "(\\s*\\d+.?+\\d*\\s*)|"+namePattern;



	// private constructor
	private DoublePattern(){
	}

	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static DoublePattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(doublePattern);
	}

	@Override
	public String getMyTypeName() {
		return name;
	}

	@Override
	public String getPureAssignment() {
		return doublePureAssignment;
	}
}
