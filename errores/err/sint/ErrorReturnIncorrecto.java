package errores.err.sint;

import errores.Error;

public class ErrorReturnIncorrecto extends Error {

	public ErrorReturnIncorrecto(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ], return mal construido."
			+ "Sólo se permite una expresión o nada");
		
	}
	
}
