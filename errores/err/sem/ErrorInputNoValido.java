package errores.err.sem;

import errores.Error;

public class ErrorInputNoValido extends Error {
	
	public ErrorInputNoValido(int lineaSemantico, sintsem.tipo.Tipo tipoRecibido) {
		super(lineaSemantico,
			  "Sentencia input no válida, tipo de expresión recibido ["+tipoRecibido
			 +"] es distinto de entero o cadena.");
	}
			

}
