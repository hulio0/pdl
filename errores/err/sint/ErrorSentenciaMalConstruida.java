package errores.err.sint;

import errores.Error;

public class ErrorSentenciaMalConstruida extends Error{
	
	public ErrorSentenciaMalConstruida(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Sentencia mal construida. "
			+ "Sólo se permiten asignaciones, llamadas a funciones, sentencias "
			+ "de print, input, return y condicionales (if/else). Todas las sentencias "
			+ "terminan con un punto y coma (;)");
		
	}

}
