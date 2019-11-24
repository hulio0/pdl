package errores.err.lex;

import errores.Error;

public class ErrorComentarioMalForm extends Error{
		
	public ErrorComentarioMalForm(int linea) {
		super(Error.Tipo.LEXICO,
			  linea,
			  "Los comentarios DEBEN llevar dos barras (//). Solo se ha encontrado una (/).");
	}
}
