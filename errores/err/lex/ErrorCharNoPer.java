package errores.err.lex;

import errores.Error;

public class ErrorCharNoPer extends Error {
	
	public ErrorCharNoPer(char c,int linea) {
		super(Error.Tipo.LEXICO,
			  linea,
			  "Caracter '"+c+"' no esta permitido en este contexto.");
	}
}
