// Create a Package
package br.horizon.compilador.analisadores;

//Importing Packages
import br.horizon.compilador.utils.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Lexico {
	private FileLoader fl;
	private StringBuilder lexema;
	private ErrorHandler eh;
	private long ic;
	private char readCharacter = ' ';
	private char c = ' ';
	
	public Lexico(String filename) throws FileNotFoundException {
		this.fl = new FileLoader(filename);		
	}
	
	public Token nextToken() throws ErroLexicoException, IOException {
		
		while (true) {
			lexema = new StringBuilder();
			
			try {
				
				Token t = null;
				
				//***** verifica fim de arquivo *****
				
				try {
					c = fl.getNextChar();
				} catch (EOFException e) {
					return new Token(TokenType.EOF, "");
				}
				
				//***** descarte espaços em branco *****
				while(Character.isWhitespace(c)) {
					c = fl.getNextChar();
				}
				
				//***** eliminar comentários: {# ... #} *****
				do {
					try {
						readCharacter = fl.getNextChar();
					} catch (Exception e) {
						genError("Comment Sequence");
						break;
					}
				} while (readCharacter != '#');
				
				lexema.append(c);
				
				//***** buscar token *****
				
				switch(c) {
					case '(':
						t = new Token(TokenType.L_PAR, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case ')':
						t = new Token(TokenType.R_PAR, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case '+':
						t = new Token(TokenType.ARIT_AS, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case '-':
						t = new Token(TokenType.ARIT_AS, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case '*':
						t = new Token(TokenType.ARIT_MD, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case '/':
						t = new Token(TokenType.ARIT_MD, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case ';':
						t = new Token(TokenType.TERM, lexema.toString());
						t.setLin(fl.getLine());
						t.setCol(fl.getColumn());
						return t;
					case '&':
						return processaRELOP();
					default:
						if(Character.isLetter(c)) {
							return processaID();
						} else if (Character.isDigit(c)){
							return processaNUM();
						} else {
							//***** erro léxico *****
							throw new ErroLexicoException();
						}
				}
			} catch (IOException e) {
				//***** fallback: erro de leitura, interromper processamento
				return new Token(TokenType.EOF, "<mensagem erro>");
			} catch (ErroLexicoException ele) {
				//***** trata o erro léxico (registra) *****
				//ErrorHandler.getInstance().registraErro(ele);
			}
		}
	}

	private void genError(String lexema) {
		// ***** gerar erro *****
		Error error = new Error();
		eh.errorRegister(error);
	}

	private Token processaNUM() {
		//  Auto-generated method stub
		return null;
	}

	private Token processaRELOP() throws IOException {
		try {
			insertLexema();
			c = fl.getNextChar();
			
			if (c == 'l' | c == 'g') {
				insertLexema();
				c = fl.getNextChar();
				
				if (c == 't' | c == 'e') {
					insertLexema();
					return genToken(TokenType.RELOP);
				}
				
				else {
					insertLexema();
					genError(lexema.toString());
					lexema.setLength(0);
				}
			}
			
			else if (c == 'e') {
				insertLexema();
				c = fl.getNextChar();
				
				if (c == 'q') {
					insertLexema();
					return genToken(TokenType.RELOP);
				}
				
				else {
					insertLexema();
					genError(lexema.toString());
					lexema.setLength(0);
				}
			}
			
			else if (c == 'd') {
				insertLexema();
				c = fl.getNextChar();
				
				if (c == 'f') {
					insertLexema();
					return genToken(TokenType.RELOP);
				}
				
				else {
					insertLexema();
					genError(lexema.toString());
					lexema.setLength(0);
				}
			}
			
			else {
				insertLexema();
				genError(lexema.toString());
				lexema.setLength(0);
			}
		} catch (Exception e) {
        	fl.resetLastChar();
        	genError(lexema.toString());
		}
		return null;
	}

	private Token genToken(TokenType relop) {
		// Auto-generated method stub
		return null;
	}

	private void insertLexema() {
		if(!Character.isWhitespace(c)) {
			lexema.append(c);
		}
	}

	private Token processaID() throws IOException {
		Token t = null;
		
		try {
			insertCharacter();
			while (t == null) {
				readCharacter = fl.getNextChar();
				if (Character.isLetter(readCharacter) || Character.isDigit(readCharacter) || readCharacter == ' ' ) {
					insertCharacter();
				} else {
					fl.resetLastChar();
					t = TabSimbolos.getInstance().addToken(lexema.toString(), fl.getLine(), ic);
				}
			}
		} catch (Exception e) {
			fl.resetLastChar();
			t = TabSimbolos.getInstance().addToken(lexema.toString(), fl.getLine(), ic);
		}
		
		return t;
	}

	private void insertCharacter() {
		if(!Character.isWhitespace(readCharacter)) {
			lexema.append(readCharacter);
		}
	}
}
