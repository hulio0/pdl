package errores.err.sint;

import errores.Error;

public class ErrorIfMalTerminado extends Error{

	public ErrorIfMalTerminado(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ], if mal terminado. "
			 +"A continuación de un if puedes introducir una sentencia "
			 +"o bien un bloque de ellas (que sean válidas) o bien un else.");
		
	}
	
}
