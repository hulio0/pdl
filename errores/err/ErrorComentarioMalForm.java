package errores.err;

public class ErrorComentarioMalForm implements Error{
	
	private int linea;
	
	public ErrorComentarioMalForm(int linea) {
		this.linea=linea;
	}
	
	public String getDesc() {
		return "Error LEXICO-->(LINEA "+linea+") "
		     + "Los comentarios DEBEN llevar dos barras (//). Solo se ha encontrado una (/).";
	}
	

}
