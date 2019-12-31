package errores.err.sem;

import errores.Error;
import sintsem.tipo.Tupla;

public class ErrorTiposArgumentosNoValidos extends Error {
	
	public ErrorTiposArgumentosNoValidos(int lineaSemantico, Tupla argsRecibidos, Tupla argsEsperados) {
		super(lineaSemantico,
			  "Llamada a función no válida, argumentos recibidos ["+argsRecibidos+"] "
			 +"no coinciden con los parámetros de la función ["+argsEsperados+"].");
	}
			

}
