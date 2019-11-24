package errores.err.sint;

import errores.Error;

public class ErrorCuerpoIfElseIncorrecto extends Error{
	
	public ErrorCuerpoIfElseIncorrecto(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Cuerpo de expresión "
			 +"condicional (if/else) NO válido. Dentro de las llaves "
			 +"sólo se permiten sentencias o bien nada (aunque hacer esto "
			 +"último no tiene mucho sentido)");
		
	}
	
}
