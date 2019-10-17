package errores.err;

public class ErrorCharNoPer implements Error {
	
	// El carácter en cuestión
	private char c;

	// Línea donde se ha encontrado
	private int linea;

	public ErrorCharNoPer(char c,int linea) {
		this.linea=linea;
		this.c=c;
	}

	@Override
	public String getDesc() {
		return "Error L�XICO-->(LINEA "+linea+") "
		     + "Car�cter '"+c+"' no est� permitido en este contexto.";
	}

}
