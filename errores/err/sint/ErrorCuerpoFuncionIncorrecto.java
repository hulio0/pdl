package errores.err.sint;
import errores.Error;

public class ErrorCuerpoFuncionIncorrecto extends Error {


	public ErrorCuerpoFuncionIncorrecto(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Cuerpo función NO válido. "
			 +"Dentro de una función sólo se permiten sentencias y declaración "
			 +"de variables");
		
	}
	
}
