package oop.ex6.Scopes.Parsers;
import oop.ex6.Line;
import oop.ex6.Regex.RegexException;
import oop.ex6.Scopes.LogicException;
import oop.ex6.Scopes.MethodScope;
import oop.ex6.Scopes.OuterScope;
import java.io.*;
import java.util.regex.*;

/**
 * Class for most external parser, that parses through the original file lines, and creates scope and methods
 * object from the given data.
 * @author shaharna13
 * @author dori203
 */
public class FileParser {


	//************************************* REGEX PATTERNS******************************************


	/*Pattern of method declaration*/
	private Pattern methodDetector = Pattern.compile("(?<void>void)\\s*([a-zA-Z]+[\\d\\w]*)\\s*\\" +
			"(([\\d\\w\\s,]*)\\)(\\s*\\{\\s*)");
	/*Pattern of an opening Paranthes.*/
	private Pattern openParanthes = Pattern.compile("(\\{)");
	/*Pattern of an closing Paranthes.*/
	private Pattern closedParanthes = Pattern.compile("(})");
	/*File path to process.*/
	private String filePath;
	/*Most external scope.*/
	private OuterScope outerScope;
	/*Current line*/
	private String curLine;
	/*Reader object.*/
	private BufferedReader myBuffer;
	/*Assisting line counter for indexing*/
	private int lineCounter;

	//************************************* METHODS******************************************


	/**
	 * Constructor receiving a file path to parse.
	 * @param filePath the file path to parse.
	 */
	public FileParser(String filePath) {
		this.filePath = filePath;
		outerScope = new OuterScope(null);
	}


	/**
	 * Main parser for the outer scope. Extracts methods, adds other lines into scope.myLines.
	 * @return an outer scope
	 * @throws IOException io Exception
	 * @throws RegexException textual exception
	 * @throws LogicException logical exception
	 */
	public OuterScope parse() throws IOException, RegexException, LogicException{
		try(Reader inputReader = new FileReader(filePath);
			BufferedReader inputBuffer = new BufferedReader(inputReader)){
			myBuffer = inputBuffer;
			curLine = inputBuffer.readLine();
			if ((curLine!=null)&&(!curLine.contains("//"))){
				curLine = curLine.trim();
			}
			lineCounter++;
			while (curLine != null) {
				Matcher curMatcher = methodDetector.matcher(curLine);
				if (curMatcher.matches()){
					extractMethod(curMatcher);
				}
				else {
					outerScope.myLines.add(new Line(curLine, lineCounter));
				}
				curLine = myBuffer.readLine();
				if ((curLine!=null)&&(!curLine.contains("//"))){
					curLine = curLine.trim();
				}
				lineCounter++;
			}
			return outerScope;
		}
		catch (IOException e){
			throw new IOException();
		}
	}

	/*
	 * Counts parentheses in order to set the limits of a method, creates new method objects.
	 * @throws IOException io Exception
	 * @param curMatcher
	 * @throws RegexException
	 * @throws LogicException
	 * @throws IOException
	 */
	private void extractMethod(Matcher curMatcher) throws RegexException, LogicException, IOException{
		String methodName = curMatcher.group(2);
		String methodParams = curMatcher.group(3).trim();
		String lastLine;
		int paranthesCounter = 1;
		MethodScope curMethod = new MethodScope(outerScope, methodName, methodParams);
		outerScope.myMethods.add(curMethod);
		while (paranthesCounter > 0) {
			curLine = myBuffer.readLine();
			if ((curLine!=null)&&(!curLine.contains("//"))){
				curLine = curLine.trim();
			}
			lineCounter++;
			if ((paranthesCounter !=0)&&(curLine==null)) {
				throw new RegexException();
			}
			Matcher matchOpenParanthes = openParanthes.matcher(curLine);
			Matcher matchClosedParanthes = closedParanthes.matcher(curLine);
			if (matchOpenParanthes.find()){
				paranthesCounter++;
			}
			else if (matchClosedParanthes.find()){
				paranthesCounter--;
			}
			curMethod.myLines.add(new Line(curLine, lineCounter));
		}
		curMethod.myLines.removeLast();
		lastLine = curMethod.myLines.getLast().getContent();
		curMatcher = Pattern.compile("\\s*return\\s*;\\s*").matcher(lastLine);
		if(!curMatcher.matches()){
			throw new RegexException();
		}
	}
}
