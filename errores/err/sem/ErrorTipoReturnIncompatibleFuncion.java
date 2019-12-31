package errores.err.sem;

import errores.Error;

public class ErrorTipoReturnIncompatibleFuncion extends Error {
	
	public ErrorTipoReturnIncompatibleFuncion(int lineaSemantico,
											  sintsem.tipo.Tipo tipoRecibido,
											  sintsem.tipo.Tipo tipoEsperado) {
		super(lineaSemantico,
			  "Tipo de sentencia return ["+tipoRecibido+"] incompatible "
			  +"con el tipo de retorno esperado ["+tipoEsperado+"].");
	}
			

}
