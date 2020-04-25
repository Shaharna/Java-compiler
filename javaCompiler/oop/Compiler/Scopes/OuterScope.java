package oop.ex6.Scopes;

import oop.ex6.Scopes.Parsers.ScopeParser;

import java.util.LinkedList;

/**
 * Class for outer Scope parser, which is responsible for the outer scope
 * @author shaharna13
 * @author dori203
 */

public class OuterScope extends Scope{


	/**Linked List of methods defined in this scope**/
	public LinkedList<MethodScope> myMethods = new LinkedList<MethodScope>();

	/**
	 * Constructor
 	 * @param parent the scope parent
	 */
	public OuterScope(Scope parent){
		myParent = parent;
		myParser = new ScopeParser(this);
	}

	@Override
	public OuterScope getMyOuter() {
		return this;
	}
}
