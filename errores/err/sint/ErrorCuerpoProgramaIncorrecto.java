package errores.err.sint;

import errores.Error;

public class ErrorCuerpoProgramaIncorrecto extends Error{

	public ErrorCuerpoProgramaIncorrecto(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Cuerpo programa NO válido. "
			 +"Dentro de un programa sólo se permiten declaración de "
			 +"funciones, variables y sentencias");
		
	}
	
}
