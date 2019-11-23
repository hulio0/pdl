package errores.err.lex;

import errores.Error;

public class ErrorEnteroFueraDeRango implements Error{
	
	// Linea donde se ha encontrado
	private int linea;
	
	public ErrorEnteroFueraDeRango(int linea) {
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error LEXICO-->(LINEA "+linea+") "
		     + "Numero fuera de rango.";
	}
	
	

}
