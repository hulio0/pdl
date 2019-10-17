package errores.err;

public class ErrorEnteroFueraDeRango implements Error{
	
	// Numero que se pretendia introducir
	private int num;
	
	// Linea donde se ha encontrado
	private int linea;
	
	public ErrorEnteroFueraDeRango(int num,int linea) {
		this.num=num;
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error L�XICO-->(LINEA "+linea+") "
		     + "Numero "+num+" est� fuera de rango.";
	}
	
	

}
