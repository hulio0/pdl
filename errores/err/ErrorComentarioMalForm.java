package errores.err;

public class ErrorComentarioMalForm implements Error{
	
	private int linea;
	
	public ErrorComentarioMalForm(int linea) {
		this.linea=linea;
	}
	
	public String getDesc() {
		return "Error L�XICO-->(LINEA "+linea+") "
		     + "Los comentarios DEBEN llevar dos barras (//). S�lo se ha encontrado una (/).";
	}
	

}
