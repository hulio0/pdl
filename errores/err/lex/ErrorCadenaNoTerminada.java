package errores.err.lex;

import errores.Error;

public class ErrorCadenaNoTerminada extends Error{
	
	public ErrorCadenaNoTerminada(String cad,int linea) {
		super(Error.Tipo.LEXICO, 
			  linea,
			  "Cadena ["+cad+"] NO ha sido terminada con su comilla de CIERRE ['].");
	}	
}
