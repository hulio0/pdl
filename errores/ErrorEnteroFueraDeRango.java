package errores;

public class ErrorEnteroFueraDeRango implements Error{
	
	// N�mero que se pretend�a introducir
	private int num;
	
	// L�nea donde se ha encontrado
	private int linea;
	
	public ErrorEnteroFueraDeRango(int num,int linea) {
		this.num=num;
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "(LINEA "+linea+") N�mero "+num+" est� fuera de rango.";
	}
	
	

}
