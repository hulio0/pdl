package errores.err.lex;

import errores.Error;

public class ErrorComentarioMalForm extends Error{
		
	public ErrorComentarioMalForm() {
		super(Error.Tipo.LEXICO,
			  "Los comentarios DEBEN llevar dos barras (//). Solo se ha encontrado una (/).");
	}
}
