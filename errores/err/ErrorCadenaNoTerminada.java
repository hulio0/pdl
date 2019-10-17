package errores.err;

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
		return "Error LÉXICO-->(LINEA "+linea+") "
		     + "Cadena ["+cad+"] NO ha sido terminada con su comilla de CIERRE (').";
	}
	
	
}
