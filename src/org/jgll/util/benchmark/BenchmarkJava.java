package org.jgll.util.benchmark;

import java.io.File;
import java.io.IOException;

import org.jgll.grammar.Grammar;
import org.jgll.grammar.symbol.Nonterminal;
import org.jgll.parser.GLLParser;
import org.jgll.parser.ParseResult;
import org.jgll.parser.ParserFactory;
import org.jgll.util.Configuration;
import org.jgll.util.Input;
import org.jgll.util.Visualization;

/**
 * 
 * @author Ali Afroozeh
 *
 */
public class BenchmarkJava extends AbstractBenchmark {
	
	private static Grammar grammar = getGrammar("grammars/csharp/specification/csharp");
	
	private static Nonterminal startSymbol = Nonterminal.withName("start[CompilationUnit]"); 
	
	public static void main(String[] args) throws Exception {
		parseFile(new File("/Users/aliafroozeh/test.cs"));
//		for (File f : find("/Users/aliafroozeh/corpus/CSharp/output", "cs")) {
//			System.out.println(f);
//			parseFile(f);
//		}
	}

	private static void parseFile(File f) throws IOException {
		Input input = Input.fromFile(f);
		GLLParser parser = ParserFactory.getParser(Configuration.DEFAULT, input, grammar);
		ParseResult result = parser.parse(input, grammar, startSymbol);
		if (result.isParseSuccess()) {
			Visualization.generateSPPFGraph("/Users/aliafroozeh/output", result.asParseSuccess().getRoot(), parser.getRegistry(), input);
			System.out.println(BenchmarkUtil.format(input, result.asParseSuccess().getStatistics()));
		} else {
			System.out.println("Parse error: " + result.asParseError());
			System.exit(0);
		}
	}
	
}