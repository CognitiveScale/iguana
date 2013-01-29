package org.jgll.parser;

import org.jgll.sppf.NonPackedNode;
import org.jgll.util.HashCode;

/**
 * 
 * 
 * @author Ali Afroozeh
 * 
 * The values needed to uniquely identify a GSS edge are
 * the Source GSS node's grammar label, input index,
 * SPPF node's grammar label, and the Destination GSS node's
 * grammar label and input index.
 * 
 * Note that the SPPF's left extent is equal to destination node's
 * input index and it's right extent is equal to source node's
 * input index. However, since Dummy nodes don't have input
 * indices, we use the input indices from GSS nodes to 
 * uniquely identify a GSS edge.
 * 
 * src (L1, j)
 * dst (L2, i)
 * node (L, i, j)
 *
 */
public class GSSEdge {

	private final GSSNode src;
	private final NonPackedNode sppfNode;
	private final GSSNode dst;
	
	private final int hash;
	
	public GSSEdge(GSSNode source, NonPackedNode sppfNode, GSSNode dst) {
		this.src = source;
		this.sppfNode = sppfNode;
		this.dst = dst;
		
		hash = HashCode.hashCode(src.getLabel(), 
								 src.getIndex(), 
								 sppfNode.getGrammarIndex(), 
								 dst.getIndex(), 
								 dst.getLabel());
	}
	
	public NonPackedNode getSppfNode() {
		return sppfNode;
	}
	
	public GSSNode getDestination() {
		return dst;
	}
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) {
			return true;
		}
		
		if(! (o instanceof GSSEdge)) {
			return false;
		}
		
		GSSEdge other = (GSSEdge) o;
		
		return hash == other.hash && 
			   src.getLabel() == other.src.getLabel() &&
			   src.getIndex() == other.src.getIndex() &&
			   sppfNode.getGrammarIndex() == other.sppfNode.getGrammarIndex() &&
			   dst.getIndex() == other.dst.getIndex() &&
			   dst.getLabel() == other.dst.getLabel();
		
	}
	
}
