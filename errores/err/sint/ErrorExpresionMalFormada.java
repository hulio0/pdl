package errores.err.sint;

import errores.Error;

public class ErrorExpresionMalFormada extends Error{
	
	public ErrorExpresionMalFormada(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Expresión mal construida");
		
	}
	

}
