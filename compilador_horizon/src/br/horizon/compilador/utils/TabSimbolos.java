package br.horizon.compilador.utils;

public class TabSimbolos {

	private static TabSimbolos instance = new TabSimbolos();
	
	private Map<String, Token> tabela;
	
	private TabSimbolos() {
		tabela = new HashMap<String, Token>();
		// Pré-carregar a tabela com todas a PALAVRAS RESERVADAS
		tabela.put("for", new Token(TokenType.FOR, "for"));
		tabela.put("while", new Token(TokenType.WHILE, "while"));
		// ...
	}

	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public void printTabela() {
		
	}
	
	

}
