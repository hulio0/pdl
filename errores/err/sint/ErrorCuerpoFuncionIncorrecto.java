package errores.err.sint;
import errores.Error;

public class ErrorCuerpoFuncionIncorrecto extends Error {


	public ErrorCuerpoFuncionIncorrecto(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Cuerpo función NO válida. "
			 +"Dentro de una función sólo se permite declaración de "
			 +"variables y sentencias");
		
	}
	
}
