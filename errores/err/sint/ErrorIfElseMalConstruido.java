package errores.err.sint;

import errores.Error;

public class ErrorIfElseMalConstruido extends Error {
	
	public static final String IF = "if";
	public static final String ELSE = "else";

	public ErrorIfElseMalConstruido(String recibido,String ifElse) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ], "+ifElse+" mal construido. "
			 +"Por favor, después de la expresión condicional del "+ifElse
			 +" escribe o bien una sentencia o bien un bloque de ellas, "
			 +"en cuyo caso, dentro de llaves: {}.");
		
	}
	
}
