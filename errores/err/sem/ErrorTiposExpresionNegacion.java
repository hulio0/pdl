package errores.err.sem;

import errores.Error;

public class ErrorTiposExpresionNegacion extends Error {
	
	public ErrorTiposExpresionNegacion(int lineaSemantico, sintsem.tipo.Tipo tipoRecibido) {
		super(lineaSemantico,
			  "Expresión negación no válida: tipos incorrectos. "
			 +"Se esperaba una expresión lógica y se ha recibido "
			 +"una expresión de tipo "+tipoRecibido);
		
	}
			

}
