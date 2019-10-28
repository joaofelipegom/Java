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
	
	public Lexico(String filename) throws FileNotFoundException {
		this.fl = new FileLoader(filename);		
	}
	
	public Token nextToken() throws ErroLexicoException, IOException {
		while (true) {
			lexema = new StringBuilder();
			
			try {
				char c;
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
				ErrorHandler.getInstance().registraErro(ele);
			}
		}
	}

	private void genError(String lexema) {
		// ***** gerar erro *****
		Error error = new Error();
		eh.errorRegister(error);
	}

	private Token processaNUM() {
		// TODO Auto-generated method stub
		return null;
	}

	private Token processaRELOP() {
		// TODO Auto-generated method stub
		return null;
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
