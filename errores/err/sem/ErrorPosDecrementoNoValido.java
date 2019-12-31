package errores.err.sem;

import errores.Error;

public class ErrorPosDecrementoNoValido extends Error {
	
	public ErrorPosDecrementoNoValido(int lineaSemantico, sintsem.tipo.Tipo tipoID) {
		super(lineaSemantico,
			  "Pos-decremento no válido, tipo recibido ["+tipoID
			 +"] es distinto de entero");
	}
			

}
