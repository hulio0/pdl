package errores.err;

public class ErrorCadenaVariasLineas implements Error{
	
	// Cadena hasta ese punto
	private String cad;
	
	// Linea donde se ha encontrado
	private int linea;
	
	public ErrorCadenaVariasLineas(String cad,int linea) {
		this.cad=cad;
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error L�XICO-->(LINEA "+linea+") "
		     + "Cadena ["+cad+"] incluye saltos de l�nea. "
		     + "Las cadenas DEBEN declararse en una �NICA l�nea.";
	}

}
