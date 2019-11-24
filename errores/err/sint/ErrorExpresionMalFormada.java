package errores.err.sint;

import errores.Error;

public class ErrorExpresionMalFormada extends Error{
		
	public ErrorExpresionMalFormada(String recibido) {
		super(Error.Tipo.SINTACTICO,
			  "No esperado [ "+recibido+" ]. Expresión mal construida. "
			 +"Por favor, asegúrate de que los operandos sean constantes (ya sea "
			 +"una constante numérica, lógica o una cadena) o que el resultado del "
			 +"operando sea una constante. Y, en caso de que los haya, que los paréntesis "
			 +"estén bien colocados. Al final, el resultado de una expresión es una constante");
		
	}
	

}
