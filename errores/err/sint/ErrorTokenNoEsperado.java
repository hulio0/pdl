package errores.err.sint;

import errores.Error;

public class ErrorTokenNoEsperado extends Error{

	public ErrorTokenNoEsperado(String tokenRecibido,int linea,String tokenEsperado) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+tokenRecibido+" ]. Se esperaba [ "+tokenEsperado+" ]");
		
	}
	
}
