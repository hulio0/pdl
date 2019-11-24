package errores;

public class Error {
	
	public static enum Tipo { LEXICO, SINTACTICO, SEMANTICO }
	
	// Línea donde se ha producido el error
	private int linea;
	private String msg;
	private Tipo tipo;
	
	public Error( Tipo tipo, int linea, String msg ) {
		this.tipo=tipo;
		this.linea=linea;
		this.msg=msg;
	}
	
	public String toString() {
		return "Error "+tipo+"-->(LINEA "+linea+"): " + msg;
	}

}
