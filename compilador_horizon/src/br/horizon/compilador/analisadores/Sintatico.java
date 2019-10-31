package br.horizon.compilador.analisadores;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.horizon.compilador.utils.ErroLexicoException;
import br.horizon.compilador.utils.TabSimbolos;
import br.horizon.compilador.utils.Token;
import br.horizon.compilador.utils.TokenType;

public class Sintatico {
	private Lexico lex;
	public Sintatico(String filename)  throws FileNotFoundException {
		this.lex = new Lexico(filename);
	}
	
	public void processar() throws ErroLexicoException, IOException {
		// Imprimir o cabeçalho de saída
		// ------------------------------------
		// (lin, col)  | Token        | Lexema
		
		// Chama nextToken até que um EOF ocorra
		Token t = lex.nextToken();
		while (t.getTokenType() != TokenType.EOF) {
			t.printToken();
			t = lex.nextToken();
		}
		
		// Imprimir a tabela de símbolos
		TabSimbolos.getInstance().printTabela();
		
		// Imprimir relatório de erros
		//ErrorHandler.getInstance().printErrorReport();
	}

}
