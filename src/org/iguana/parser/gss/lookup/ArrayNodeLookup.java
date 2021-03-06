/*
 * Copyright (c) 2015, Ali Afroozeh and Anastasia Izmaylova, Centrum Wiskunde & Informatica (CWI)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this 
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 */

package org.iguana.parser.gss.lookup;

import java.util.Arrays;
import java.util.stream.Collectors;

import iguana.utils.input.Input;
import org.iguana.grammar.slot.NonterminalGrammarSlot;
import org.iguana.parser.gss.GSSNode;
import org.iguana.util.CollectionsUtil;

public class ArrayNodeLookup extends AbstractNodeLookup {

	private GSSNode[] gssNodes;
	
	public ArrayNodeLookup(Input input) {
		gssNodes = new GSSNode[input.length()];
	}
	
	@Override
	public void reset(Input input) {
		super.reset(input);
		gssNodes = new GSSNode[input.length()];		
	}
	
	@Override
	public Iterable<GSSNode> getNodes() {
		return CollectionsUtil.concat(Arrays.stream(gssNodes).filter(n -> n != null).collect(Collectors.toList()), super.map.values());
	}

	@Override
	public void get(int i, GSSNodeCreator creator) {
		gssNodes[i] = creator.create(gssNodes[i]);
	}

	@Override
	public GSSNode get(NonterminalGrammarSlot slot, int i) {
		GSSNode node = gssNodes[i];
		if (node == null) {
			node = new GSSNode(slot, i);
			gssNodes[i] = node;
			return node;
		} 
		return node;
	}
}
