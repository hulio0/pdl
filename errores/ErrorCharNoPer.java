package errores;

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
		return "Error LÉXICO-->(LINEA "+linea+") "
		     + "Carácter '"+c+"' no es un carácter permitido en este contexto.";
	}

}
