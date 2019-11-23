package errores.err.lex;

import errores.Error;

public class ErrorCadenaVariasLineas implements Error{
	
	// Cadena hasta ese punto
	private String cad;
	
	// Linea donde se ha encontrado
	private int linea;
	
	public ErrorCadenaVariasLineas(String cad,int linea) {
		this.cad=cad.replace("/n","");
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error LEXICO-->(LINEA "+linea+") "
		     + "Cadena ["+cad+"] incluye saltos de linea. "
		     + "Las cadenas DEBEN declararse en una UNICA linea.";
	}

}
