package errores.err.sint;

import errores.Error;

public class ErrorIfMalTerminado extends Error{

	public ErrorIfMalTerminado(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ], if mal terminado. "
			 +"A continuación de un if puedes introducir una sentencia "
			 +"o bien un bloque de ellas (que sean válidas) o bien un else.");
		
	}
	
}
