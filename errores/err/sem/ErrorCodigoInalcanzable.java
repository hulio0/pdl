package errores.err.sem;

import errores.Error;

public class ErrorCodigoInalcanzable extends Error {

	public ErrorCodigoInalcanzable() {
		super(Error.Tipo.SEMANTICO,"Código inalcanzable. Ya hay sentencias return antes de este código");
	}
	
}
