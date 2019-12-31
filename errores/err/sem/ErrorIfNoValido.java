package errores.err.sem;

import errores.Error;

public class ErrorIfNoValido extends Error {
	
	public ErrorIfNoValido(int lineaSemantico, sintsem.tipo.Tipo tipoRecibido) {
		super(lineaSemantico,
			  "Sentencia if no válido, tipo de expresión recibido ["+tipoRecibido
			 +"] es distinto de lógico.");
	}
			

}
