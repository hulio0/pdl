package errores.err.sem;

import errores.Error;

public class ErrorIDYaDeclarado extends Error{

	public ErrorIDYaDeclarado(String lexVariable) {
		super(Error.Tipo.SEMANTICO,
			  "["+lexVariable+"] ya ha sido declarado previamente");
	}
	
}
