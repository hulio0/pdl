package errores.err.sem;

import errores.Error;

public class ErrorTiposExpresionMayorMenor extends Error {
	
	public ErrorTiposExpresionMayorMenor(sintsem.tipo.Tipo tipoRecibido) {
		super(Error.Tipo.SEMANTICO,
			  "Expresión mayor/menor no válida: tipos incorrectos. "
			 +"Se esperaba una expresión entera y se ha recibido "
			 +"una expresión de tipo "+tipoRecibido);
		
	}
			

}
