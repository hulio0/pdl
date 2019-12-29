package errores.err.sint;

import errores.Error;

public class ErrorArgumentoNoValido extends Error{

	public ErrorArgumentoNoValido(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Paso de argumentos NO válido. "
			 +"Por favor, después del nombre de la función y, entre paréntesis, "
			 +"escribe como argumento una expresión o bien nada (según necesite la función) "
			 +"Si la función requiere introducir múltiples argumentos, sepáralos por comas");
	}
	
}
