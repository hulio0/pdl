package errores.err.sem;

import errores.Error;

public class ErrorReturnFueraSitio extends Error {
	
	public ErrorReturnFueraSitio(int lineaSemantico) {
		super(lineaSemantico,
			  "Sentencia return fuera de función.");
	}
			

}
