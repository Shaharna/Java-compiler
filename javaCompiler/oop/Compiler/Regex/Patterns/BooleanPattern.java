package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern class for Boolean Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class BooleanPattern implements DeclarationPatterns {

	private static BooleanPattern myInstance= new BooleanPattern();

	 private static final String name = "boolean";

	 private static final String booleanPattern = "\\s*(boolean)\\s*";

	 static final String booleanPureAssignment = "(\\s*true|\\s*false|"+DoublePattern.doublePureAssignment+
			"|"+IntPattern.intPureAssignment+")";


	// private constructor
	private BooleanPattern(){
	}

	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static BooleanPattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(booleanPattern);
	}

	@Override
	public String getMyTypeName() {
		return name;
	}

	@Override
	public String getPureAssignment() {
		return booleanPureAssignment;
	}
}
