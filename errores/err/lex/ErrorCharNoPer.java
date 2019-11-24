package errores.err.lex;

import errores.Error;

public class ErrorCharNoPer extends Error {
	
	public ErrorCharNoPer(char c) {
		super(Error.Tipo.LEXICO,
			  "Caracter '"+c+"' no esta permitido en este contexto.");
	}
}
