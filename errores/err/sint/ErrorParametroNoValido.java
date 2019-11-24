package errores.err.sint;
import errores.Error;

public class ErrorParametroNoValido extends Error{

	public ErrorParametroNoValido(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Declaración de parámetro NO válida. "
			 +"Por favor, después del tipo de retorno de la función y dentro "
			 +"de los paréntesis escribe el tipo del parámetro (int,boolean, o string) "
			 +"seguido del nombre del parámetro. Si deseas introducir múltiples parámetros, sepáralos por comas");
	}
	
}
