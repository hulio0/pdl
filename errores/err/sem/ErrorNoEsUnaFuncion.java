package errores.err.sem;

import errores.Error;

public class ErrorNoEsUnaFuncion extends Error {
	
	public ErrorNoEsUnaFuncion(sintsem.tipo.Tipo tipoID) {
		super(Error.Tipo.SEMANTICO,
			  "Llamada a función no válida: no es una "
			 +"función. Es de tipo ["+tipoID+"].");
	}
			

}
