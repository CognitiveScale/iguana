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

package org.iguana.grammar.symbol;

import java.io.Serializable;
import java.util.Set;

import org.iguana.datadependent.attrs.Attr;
import org.iguana.grammar.condition.Condition;
import org.iguana.traversal.ISymbolVisitor;
import org.iguana.util.CollectionsUtil;
import org.iguana.util.generator.ConstructorCode;

/**
 * 
 * 
 * @author Ali Afroozeh
 * @author Anastasia Izmaylova
 * 
 * Symbol ::= Label ':' Symbol
 *          | Nonterminal '(' {Expression ','}+ ')'
 *          
 *          | ...
 *          
 *          | '{' Symbol+ '}'
 *          > "align" Symbol
 *          | "offside" Symbol 
 *          > Symbol "do" Statement
 *          | Symbol "when" Expression
 *          > "if" '(' Expression ')' Symbol
 *          | "if" '(' Expression ')' Symbol "else" Symbol
 *          | "while" '(' Expression ')' Symbol
 *          
 *
 */
public interface Symbol extends ConstructorCode, Serializable, Attr {
	
	public String getName();
	
	public Set<Condition> getPreConditions();
	
	public Set<Condition> getPostConditions();
	
	default Set<Condition> getConditions() {
		return CollectionsUtil.union(getPreConditions(), getPostConditions());
	}
	
	default boolean hasConditions() {
		return !getConditions().isEmpty();
	}
	
	public Object getObject();
	
	public String getLabel();
	
	public SymbolBuilder<? extends Symbol> copyBuilder();
	
	default int size() {
		return 1;
	}
		
	public String toString(int j);
	
	public <T> T accept(ISymbolVisitor<T> visitor);
}	
