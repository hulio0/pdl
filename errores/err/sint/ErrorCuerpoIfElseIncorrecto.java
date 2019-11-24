package errores.err.sint;

import errores.Error;

public class ErrorCuerpoIfElseIncorrecto extends Error{
	
	public ErrorCuerpoIfElseIncorrecto(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Cuerpo expresión condicional (if/else) NO válido. "
			 +"Dentro de las llaves sólo se permiten sentencias "
			 +"o bien nada (aunque esto no tiene mucho sentido)");
		
	}
	
}
