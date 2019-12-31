package errores.err.sem;

import errores.Error;

public class ErrorNoEsUnaFuncion extends Error {
	
	public ErrorNoEsUnaFuncion(String lexID, sintsem.tipo.Tipo tipoID) {
		super(Error.Tipo.SEMANTICO,
			  "Llamada a función no válida, ["+lexID+"]" + "no es una "
			 +"función. Es de tipo ["+tipoID+"].");
	}
			

}
