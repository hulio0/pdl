package errores.err.lex;

import errores.Error;

public class ErrorCharNoPer implements Error {
	
	// El carácter en cuestión
	private char c;

	// Línea donde se ha encontrado
	private int linea;

	public ErrorCharNoPer(char c,int linea) {
		this.linea=linea;
		this.c=c;
	}

	@Override
	public String getDesc() {
		return "Error LEXICO-->(LINEA "+linea+") "
		     + "Caracter '"+c+"' no esta permitido en este contexto.";
	}

}
