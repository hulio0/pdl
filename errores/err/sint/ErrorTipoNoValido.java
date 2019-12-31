package errores.err.sint;
import errores.Error;

public class ErrorTipoNoValido extends Error{

	public ErrorTipoNoValido(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "[ "+recibido+" ] NO es un tipo válido. "
			 +"Por favor, introduce int,boolean o string. Si estás especificando "
			 +"el tipo de retorno de una función también se permite no escribir nada (lo "
			 +"cual se interpreta como void).");
	}

}
