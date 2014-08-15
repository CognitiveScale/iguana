package org.jgll.parser;

import static org.jgll.util.CollectionsUtil.*;
import static org.junit.Assert.*;

import org.jgll.grammar.Grammar;
import org.jgll.grammar.symbol.Character;
import org.jgll.grammar.symbol.Nonterminal;
import org.jgll.grammar.symbol.Rule;
import org.jgll.sppf.IntermediateNode;
import org.jgll.sppf.NonterminalNode;
import org.jgll.sppf.PackedNode;
import org.jgll.sppf.SPPFNode;
import org.jgll.sppf.SPPFNodeFactory;
import org.jgll.sppf.TokenSymbolNode;
import org.jgll.util.Input;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * S ::= a S b
 *     | a S
 *     | s
 * 
 * @author Ali Afroozeh
 *
 */
public class DanglingElseGrammar4 {

	Nonterminal S = Nonterminal.withName("S");
	Character s = Character.from('s');
	Character a = Character.from('a');
	Character b = Character.from('b');
	private Grammar grammar;

	@Before
	public void init() {
		
		Grammar.Builder builder = new Grammar.Builder();
		
		Rule rule1 = new Rule(S, list(a, S, b));
		builder.addRule(rule1);
		
		Rule rule2 = new Rule(S, list(a, S));
		builder.addRule(rule2);
		
		Rule rule3 = new Rule(S, list(s));
		builder.addRule(rule3);
		
		grammar = builder.build();
	}
	
	@Test
	public void test() {
		Input input = Input.fromString("aasb");
		GLLParser parser = ParserFactory.originalParser(grammar, input);
		ParseResult result = parser.parse(input, grammar.toGrammarGraph(), "S");
		assertTrue(result.isParseSuccess());
		assertTrue(result.asParseSuccess().getRoot().deepEquals(getExpectedSPPF()));
	}
	
	private SPPFNode getExpectedSPPF() {
		SPPFNodeFactory factory = new SPPFNodeFactory(grammar.toGrammarGraph());
		NonterminalNode node1 = factory.createNonterminalNode("S", 0, 4).init();
		PackedNode node2 = factory.createPackedNode("S ::= a S b .", 3, node1);
		IntermediateNode node3 = factory.createIntermediateNode("S ::= a S . b", 0, 3).init();
		PackedNode node4 = factory.createPackedNode("S ::= a S . b", 1, node3);
		TokenSymbolNode node5 = factory.createTokenNode("a", 0, 1);
		NonterminalNode node6 = factory.createNonterminalNode("S", 1, 3).init();
		PackedNode node7 = factory.createPackedNode("S ::= a S .", 2, node6);
		TokenSymbolNode node8 = factory.createTokenNode("a", 1, 1);
		NonterminalNode node9 = factory.createNonterminalNode("S", 2, 3).init();
		PackedNode node10 = factory.createPackedNode("S ::= s .", 2, node9);
		TokenSymbolNode node11 = factory.createTokenNode("s", 2, 1);
		node10.addChild(node11);
		PackedNode node12 = factory.createPackedNode("S ::= s .", 2, node9);
		node12.addChild(node11);
		node9.addChild(node10);
		node9.addChild(node12);
		node7.addChild(node8);
		node7.addChild(node9);
		PackedNode node13 = factory.createPackedNode("S ::= a S .", 2, node6);
		node13.addChild(node8);
		node13.addChild(node9);
		node6.addChild(node7);
		node6.addChild(node13);
		node4.addChild(node5);
		node4.addChild(node6);
		node3.addChild(node4);
		TokenSymbolNode node14 = factory.createTokenNode("b", 3, 1);
		node2.addChild(node3);
		node2.addChild(node14);
		PackedNode node15 = factory.createPackedNode("S ::= a S .", 1, node1);
		NonterminalNode node16 = factory.createNonterminalNode("S", 1, 4).init();
		PackedNode node17 = factory.createPackedNode("S ::= a S b .", 3, node16);
		IntermediateNode node18 = factory.createIntermediateNode("S ::= a S . b", 1, 3).init();
		PackedNode node19 = factory.createPackedNode("S ::= a S . b", 2, node18);
		node19.addChild(node8);
		node19.addChild(node9);
		PackedNode node20 = factory.createPackedNode("S ::= a S . b", 2, node18);
		node20.addChild(node8);
		node20.addChild(node9);
		node18.addChild(node19);
		node18.addChild(node20);
		node17.addChild(node18);
		node17.addChild(node14);
		PackedNode node21 = factory.createPackedNode("S ::= a S b .", 3, node16);
		node21.addChild(node18);
		node21.addChild(node14);
		node16.addChild(node17);
		node16.addChild(node21);
		node15.addChild(node5);
		node15.addChild(node16);
		node1.addChild(node2);
		node1.addChild(node15);
		return node1;
	}

}
