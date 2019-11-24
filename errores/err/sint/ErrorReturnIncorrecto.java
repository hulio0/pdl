package errores.err.sint;

import errores.Error;

public class ErrorReturnIncorrecto extends Error {

	public ErrorReturnIncorrecto(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ], return mal construido."
			+ "Sólo se permite una expresión o nada");
		
	}
	
}
