package errores.err.lex;

import errores.Error;

public class ErrorCadenaVariasLineas extends Error{
	
	
	public ErrorCadenaVariasLineas(String cad,int linea) {
		super(Error.Tipo.LEXICO,
			  linea,
			  "Cadena ["+cad+"] incluye saltos de linea. " + 
			  "Las cadenas DEBEN declararse en una UNICA linea.");
	}

}
