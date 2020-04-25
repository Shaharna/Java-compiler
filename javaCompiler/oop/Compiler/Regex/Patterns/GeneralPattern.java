package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern class for General sjava line Structure, meaning it combines all the given options in a
 * sjava correct line.
 * @author shaharna13 dori203
 * @see DeclarationPatterns
 */

public class GeneralPattern {

	private static final String RETURN = "\\s*return\\s*;\\s*";
	private static final String COMMENT = "//.*";
	private static GeneralPattern myInstance= new GeneralPattern();


	// private constructor
	private GeneralPattern(){
	}

	/**
	 * The Pattern instance as it is a singleton class
	 * @return myInstance
	 */
	public static GeneralPattern getMyInstance(){
		return myInstance;
	}



	//************************************* REGEX PATTERNS******************************************

	private static final String namePattern = "\\s*([a-zA-Z][\\w]*|[_]+[\\w]*)\\s*";


	private  final static  String generalPureAssignment = "("+StringPattern.stringAssignmentName+"|"
			+IntPattern.intAssignmentName+"|" +DoublePattern.doubleAssignmentName+"|"
			+BooleanPattern.booleanPureAssignment+"|"+CharPattern.charAssignmentName+")";


	private  final static  String generalNoNameAssignment = "("+StringPattern.stringPureAssignment+"|"
				+IntPattern.intPureAssignment+"|" +DoublePattern.doublePureAssignment+"|"
				+BooleanPattern.booleanPureAssignment+"|"+CharPattern.charPureAssignment+")";


	private static final String methodCall = namePattern+"([(]\\s*((("+namePattern+"|"+
			generalPureAssignment+")\\s*,)*("+namePattern+"|"+generalPureAssignment+"\\s*))?[)])\\s*;\\s*";


	private static final String assignmentLine =
			"(\\s*"+namePattern+"\\s*=\\s*"+generalPureAssignment+")\\s*;\\s*";


	private final String generalAssignment =
			namePattern+"\\s*(;|=\\s*("+generalPureAssignment+")\\s*;))*";


	private final String generalSecondAssignment =
				"(("+namePattern+"\\s*(,|=\\s*"+generalPureAssignment+",))*";



	//************************************* METHODS ******************************************


	/**
	 *
	 * @param allTypes all the types available in the s java file
	 * @return a general line as accepted in the sjava file
	 */
	public Pattern getMyGeneralPattern(String allTypes){
		String generalPattern = "(void|\\}|"+allTypes+")?";
		return Pattern.compile("\\s*(final)*\\s*"+generalPattern+generalSecondAssignment+generalAssignment+"|"
				+methodCall+ "|" + RETURN+"|"+ COMMENT+"|"+OpenBlockPattern.openBlockString);
	}


	/**
	 *Getter
	 * @return a pattern with no reference assignment option.
	 */
	public Pattern getNoNamePattern(){
		return Pattern.compile(generalNoNameAssignment);
	}

	/**
	 *Getter
	 * @return a full assignment line
	 */
	public Pattern getMyAssignmentPattern(){
		return Pattern.compile(assignmentLine+"|"+namePattern);
	}

	/**
	 * Getter
	 * @return a "return;" pattern.
	 */
	public Pattern getReturnPattern(){
		return Pattern.compile(RETURN);
	}

}
