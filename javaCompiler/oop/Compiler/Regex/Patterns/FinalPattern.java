package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;


/**
 * Pattern class for Final Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class FinalPattern implements DeclarationPatterns {

	private static FinalPattern myInstance= new FinalPattern();

	 private static final String name = "final";

	 private static final String finalPattern = "\\s*(final)";

	//private constructor
	private FinalPattern(){
	}

	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static FinalPattern getMyInstance(){
		return myInstance;
	}

	@Override
	public Pattern getMyType() {
		return Pattern.compile(finalPattern);
	}

	@Override
	public String getMyTypeName() {
		return name;
	}
	@Override
	public String getPureAssignment() {
		return null;
	}

}
