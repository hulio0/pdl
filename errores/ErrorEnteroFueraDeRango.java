package errores;

public class ErrorEnteroFueraDeRango implements Error{
	
	// Número que se pretendía introducir
	private int num;
	
	// Línea donde se ha encontrado
	private int linea;
	
	public ErrorEnteroFueraDeRango(int num,int linea) {
		this.num=num;
		this.linea=linea;
	}

	@Override
	public String getDesc() {
		return "Error LÉXICO-->(LINEA "+linea+") "
		     + "Número "+num+" está fuera de rango.";
	}
	
	

}
