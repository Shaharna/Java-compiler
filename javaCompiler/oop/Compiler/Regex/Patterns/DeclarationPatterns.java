package oop.ex6.Regex.Patterns;
import java.util.regex.Pattern;

/**
 * Pattern interface for all the declaration types
 * @author shaharna13 dori203
 */

public interface DeclarationPatterns {

	 String namePattern = "\\s*([a-zA-Z][\\w]*|[_][\\w]+)\\s*";

	/**
	 * This function will return the Patterns type as a regex pattern.
	 * @return myTypePattern
	 */
	Pattern getMyType ();


	/**
	 * returns the type name.
	 * @return the type name.
	 */
	 String getMyTypeName();

	/**
	 * get the name pattern
	 * @return namePattern
	 */
	 static String getNamePattern() {
		return namePattern;
	}
	/**
	 * get the pure pattern assignment
	 * @return the pure assignment
	 */
	 String getPureAssignment();
}