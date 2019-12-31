package errores.err.sint;

import errores.Error;

public class ErrorExpresionMalFormada extends Error{
		
	public ErrorExpresionMalFormada(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Expresión mal construida.");
		
	}
	

}
