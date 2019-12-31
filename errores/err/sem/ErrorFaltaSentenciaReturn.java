package errores.err.sem;

import errores.Error;

public class ErrorFaltaSentenciaReturn extends Error {

	public ErrorFaltaSentenciaReturn(int lineaSemantico) {
		super(lineaSemantico,"Falta sentencia return en la función.");
	}
	
}
