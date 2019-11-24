package errores.err.sint;
import errores.Error;

public class ErrorParametroNoValido extends Error{

	public ErrorParametroNoValido(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Declaración de parámetro NO válida. "
			 +"Por favor, después del tipo de retorno de la función y dentro "
			 +"de los paréntesis escribe el tipo del parámetro (int,boolean, o string) "
			 +"seguido del nombre del parámetro. Si deseas introducir múltiples parámetros, sepáralos por comas");
	}
	
}
