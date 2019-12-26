package errores.err.lex;

import errores.Error;

public class ErrorCadenaExcedeLongitudMaxima extends Error{

	public ErrorCadenaExcedeLongitudMaxima(String cad) {
		super(Error.Tipo.LEXICO,
			  "Cadena ["+cad+"] excede la longitud máxima de 64 caracteres.");

	}
}
