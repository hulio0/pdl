package errores.err.sint;

import errores.Error;

public class ErrorAsignacionOLlamadaMalConstruida extends Error{
	
	public ErrorAsignacionOLlamadaMalConstruida(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. En una sentencia, después del "
			 +"nombre de una variable solo se permite una asignación. Si el nombre "	  
			 +"hace referencia a una función entonces solo se permite una llamada a la misma.");
		
	}

}
