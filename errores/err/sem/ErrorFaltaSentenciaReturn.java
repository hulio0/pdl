package errores.err.sem;

import errores.Error;

public class ErrorFaltaSentenciaReturn extends Error {

	public ErrorFaltaSentenciaReturn() {
		super(Error.Tipo.SEMANTICO,"Falta sentencia return en la función.");
	}
	
}
