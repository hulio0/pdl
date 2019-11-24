package errores.err.sint;

import errores.Error;

public class ErrorTokenNoEsperado extends Error{

	public ErrorTokenNoEsperado(String tokenRecibido,String tokenEsperado) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+tokenRecibido+" ]. Se esperaba [ "+tokenEsperado+" ]");
		
	}
	
}
