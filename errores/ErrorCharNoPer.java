package errores;

public class ErrorCharNoPer implements Error {
	
	// El car�cter en cuesti�n
	private char c;

	// L�nea donde se ha encontrado
	private int linea;

	public ErrorCharNoPer(char c,int linea) {
		this.linea=linea;
		this.c=c;
	}

	@Override
	public String getDesc() {
		return "(LINEA "+linea+") Car�cter '"+c+"' no es un car�cter permitido en este contexto.";
	}

}
