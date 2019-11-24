package errores.err.lex;

import errores.Error;

public class ErrorCadenaNoTerminada extends Error{
	
	public ErrorCadenaNoTerminada(String cad) {
		super(Error.Tipo.LEXICO, 
			  "Cadena ["+cad+"] NO ha sido terminada con su comilla de CIERRE (').");
	}	
}
