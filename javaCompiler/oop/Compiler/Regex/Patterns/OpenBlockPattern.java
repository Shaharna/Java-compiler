package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern class for Block Structure
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class OpenBlockPattern {

	//private constructor
	private OpenBlockPattern(){
	}

	private static OpenBlockPattern myInstance = new OpenBlockPattern();

	//************************************* REGEX PATTERNS******************************************


	private static final String ifOrWhile = "(if|while)(\\s*)";

	private static final String parameters =  "(\\((\\s*.*?\\s*)*\\))";

	private static final String closingParanthes =  "(\\s*(\\{\\s*))";

	private static final String paramSeparator =  "(\\|\\|)|(&&)";

	private static Pattern paramsPattern = Pattern.compile(BooleanPattern.booleanPureAssignment+"|"+
			DeclarationPatterns.getNamePattern());

	static String openBlockString = ifOrWhile+parameters+closingParanthes;

	private static String openBlockStringComplete =
			"(?<block>\\s*(if|while)\\s*(\\((\\s*.*\\s*)*\\))\\s*\\{\\s*)";

	public static Pattern openBlockCompletePattern = Pattern.compile(openBlockStringComplete);

	private static Pattern openBlockPattern = Pattern.compile(ifOrWhile+parameters+closingParanthes);

	/** this method returns an instance of this specific pattern**/
	public Pattern getOpenBlockPattern(){
		return openBlockPattern;
	}

	public Pattern getParamsPattern(){
		return paramsPattern;
	}

	public String getParamSeparator(){
		return paramSeparator;
	}


	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static OpenBlockPattern getMyInstance(){
		return myInstance;
	}
}
