package errores.err;

public class ErrorCharNoPer implements Error {
	
	// El carÃ¡cter en cuestiÃ³n
	private char c;

	// LÃ­nea donde se ha encontrado
	private int linea;

	public ErrorCharNoPer(char c,int linea) {
		this.linea=linea;
		this.c=c;
	}

	@Override
	public String getDesc() {
		return "Error LÉXICO-->(LINEA "+linea+") "
		     + "Carácter '"+c+"' no está permitido en este contexto.";
	}

}
