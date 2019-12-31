package errores.err.sem;

import errores.Error;

public class ErrorTiposExpresionNegacion extends Error {
	
	public ErrorTiposExpresionNegacion(sintsem.tipo.Tipo tipoRecibido) {
		super(Error.Tipo.SEMANTICO,
			  "Expresión negación no válida: tipos incorrectos. "
			 +"Se esperaba una expresión lógica y se ha recibido "
			 +"una expresión de tipo "+tipoRecibido);
		
	}
			

}
