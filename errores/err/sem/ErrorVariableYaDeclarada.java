package errores.err.sem;

import errores.Error;

public class ErrorVariableYaDeclarada extends Error{

	public ErrorVariableYaDeclarada(String lexVariable) {
		super(Error.Tipo.SEMANTICO,
			  "Variable ["+lexVariable+"] ya declarada");
	}
	
}
