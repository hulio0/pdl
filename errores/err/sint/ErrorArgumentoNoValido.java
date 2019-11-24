package errores.err.sint;

import errores.Error;

public class ErrorArgumentoNoValido extends Error{

	public ErrorArgumentoNoValido(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Paso de argumentos NO válido. "
			 +"Por favor, después del nombre de la función y entre paréntesis, "
			 +"escribe como argumento una expresión o bien nada (según necesite la función) "
			 +"Si deseas introducir múltiples argumentos, sepáralos por comas");
	}
	
}
