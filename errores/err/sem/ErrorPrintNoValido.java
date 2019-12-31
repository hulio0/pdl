package errores.err.sem;

import errores.Error;

public class ErrorPrintNoValido extends Error {
	
	public ErrorPrintNoValido(int lineaSemantico, sintsem.tipo.Tipo tipoRecibido) {
		super(lineaSemantico,
			  "Sentencia print no válida, tipo de expresión recibido ["+tipoRecibido
			 +"] es distinto de entero o cadena");
	}
			

}
