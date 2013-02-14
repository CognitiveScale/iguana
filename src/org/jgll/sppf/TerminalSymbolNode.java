package org.jgll.sppf;

import java.util.Collections;

import org.jgll.traversal.SPPFVisitor;
import org.jgll.traversal.Node;

/**
 * 
 * 
 * @author Ali Afroozeh
 *
 */
public class TerminalSymbolNode extends SPPFNode implements Node {
	
	public static final int EPSILON = -2;
	
	private final int matchedChar;
	
	private final int inputIndex;
	
	public TerminalSymbolNode(int matchedChar, int inputIndex) {
		this.matchedChar = matchedChar;
		this.inputIndex = inputIndex;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}

		if (!(obj instanceof TerminalSymbolNode)) {
			return false;
		}
		
		TerminalSymbolNode other = (TerminalSymbolNode) obj;
		
		return matchedChar == other.matchedChar &&
			   inputIndex == other.inputIndex;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result += 31 * result + matchedChar;
		result += 31 * result + inputIndex;
		return result;
	}
	
	@Override
	public String getLabel() {
		return matchedChar == EPSILON ? "\u03B5" : (char) matchedChar + "";
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %d, %d)", getLabel(), inputIndex, getRightExtent());
	}
	
	@Override
	public String getId() {
		return "t" + matchedChar + "," + inputIndex + "," + getRightExtent();
	}
	
	public int getMatchedChar() {
		return matchedChar;
	}
	
	@Override
	public int getLeftExtent() {
		return inputIndex;
	}

	@Override
	public int getRightExtent() {
		return matchedChar == EPSILON ? inputIndex : inputIndex + 1;
	}

	@Override
	public void accept(SPPFVisitor visitAction) {
		visitAction.visit(this);
	}

	@Override
	public SPPFNode get(int index) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Iterable<SPPFNode> getChildren() {
		return Collections.emptyList();
	}
	
	@Override
	public int getStart() {
		return getLeftExtent();
	}
	
	@Override
	public int getOffset() {
		return getRightExtent() - getLeftExtent();
	}

	@Override
	public int getLineNumber() {
		return 0;
	}
	
	@Override
	public int getColumn() {
		return 0;
	}
}
