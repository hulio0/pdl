package errores.err.lex;

import errores.Error;

public class ErrorEnteroFueraDeRango extends Error{
	
	public ErrorEnteroFueraDeRango(String num,int linea) {		
		super(Error.Tipo.LEXICO,
			  linea,
			  "Numero "+num+" fuera de rango.");
		
	}
}
