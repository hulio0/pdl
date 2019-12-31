package errores.err.sem;

import errores.Error;

public class ErrorTipoAsignacionIncompatible extends Error {
	
	public ErrorTipoAsignacionIncompatible(int lineaSemantico,
										   sintsem.tipo.Tipo tipoID,
										   sintsem.tipo.Tipo tipoRecibido) {
		super(lineaSemantico,
			  "Tipos de asignación no compatibles. El identificador "
			  +"es de tipo ["+tipoID+"] incompatible con el tipo de"
			  +"expresión recibido ["+tipoRecibido+"].");
	}
			

}
