package oop.ex6;

/**
 * Line Wrapper, includes the line number and its content
 * @author shaharna13
 * @author dori203
 */

public class Line{

	/*Actual line content.*/
	private String content;
	/*Line index in original file.*/
	private int index;

	/**
	 * Constructor
	 * @param content the line content as string
	 * @param index the line number
	 */
	public Line(String content, int index){
		this.content = content;
		this.index = index;
	}

	/**
	 * Getter
	 * @return content
	 */
	public String getContent(){
		return content;
	}

	/**
	 * Getter
	 * @return line number
	 */
	public int getIndex(){
		return index;
	}

}
