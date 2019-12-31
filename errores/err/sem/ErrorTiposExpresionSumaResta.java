package errores.err.sem;

import errores.Error;

public class ErrorTiposExpresionSumaResta extends Error {
	
	public ErrorTiposExpresionSumaResta(sintsem.tipo.Tipo tipoRecibido) {
		super(Error.Tipo.SEMANTICO,
			  "Expresión suma/resta no válida: tipos incorrectos. "
			 +"Se esperaba una expresión entera y se ha recibido "
			 +"una expresión de tipo "+tipoRecibido);
		
	}
			

}
