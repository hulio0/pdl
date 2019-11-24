package errores.err.sint;

import errores.Error;

public class ErrorCuerpoProgramaIncorrecto extends Error{

	public ErrorCuerpoProgramaIncorrecto(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Cuerpo programa NO válido. "
			 +"Dentro de un programa sólo se permiten declaración de "
			 +"funciones, variables y sentencias");
		
	}
	
}
