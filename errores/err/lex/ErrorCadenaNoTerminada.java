package errores.err.lex;

import errores.Error;

public class ErrorCadenaNoTerminada implements Error{

	// Cadena hasta ese punto
	private String cad;
	
	// Linea donde se ha encontrado
	private int linea;
	
	public ErrorCadenaNoTerminada(String cad,int linea) {
		this.cad=cad;
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error LEXICO-->(LINEA "+linea+") "
		     + "Cadena ["+cad+"] NO ha sido terminada con su comilla de CIERRE (').";
	}
	
	
}
