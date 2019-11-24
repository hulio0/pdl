package errores;

import lexico.AnalizadorLexico;

public class Error {
	
	public static enum Tipo { LEXICO, SINTACTICO, SEMANTICO }
	
	// Línea donde se ha producido el error
	private int linea;
	private String msg;
	private Tipo tipo;
	
	public Error( Tipo tipo, String msg ) {
		this.tipo=tipo;
		this.msg=msg;
		
		// Los errores nada mas encontrarse se reportan. Luego,
		// cuando llegamos a aquí, el lexico marca la linea en la
		// se encontro
		this.linea=AnalizadorLexico.lineaActual();
	}
	
	public String toString() {
		return "Error "+tipo+"-->(LINEA "+linea+"): " + msg;
	}

}
