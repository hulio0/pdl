package errores.err.sint;
import errores.Error;

public class ErrorTipoNoValido extends Error{
	
	public ErrorTipoNoValido(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "[ "+cad+" ] NO es un tipo válido. "
			 +"Por favor, introduce int,boolean o string. Si estás especificando"
			 +"el tipo de retorno de una función también se permite no escribir nada (lo "
			 +"cual se interpreta como void)");
	}

}
