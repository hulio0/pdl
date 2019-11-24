package errores.err.lex;

import java.math.BigInteger;

import errores.Error;

public class ErrorEnteroFueraDeRango extends Error{
	
	public ErrorEnteroFueraDeRango(BigInteger num) {		
		super(Error.Tipo.LEXICO,
			  "Numero ["+num+"] fuera de rango.");
		
	}
}
