package br.horizon.compilador.analisadores;

public class Sintatico {
	private Lexico lex;
	public Sintatico(String filename)  throws FileNotFoundException {
		this.lex = new Lexico(filename);
	}
	
	public void processar() {
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
		ErrorHandler.getInstance().printErrorReport();
	}

}
