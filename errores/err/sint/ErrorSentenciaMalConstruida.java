package errores.err.sint;

import errores.Error;

public class ErrorSentenciaMalConstruida extends Error{
	
	public ErrorSentenciaMalConstruida(String cad,int linea) {
		super(Error.Tipo.SINTACTICO,
			  linea,
			  "No esperado [ "+cad+" ]. Sentencia mal construida");
		
	}

}
