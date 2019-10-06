package errores;

public class ErrorComentarioMalForm implements Error{
	
	private int linea;
	
	public ErrorComentarioMalForm(int linea) {
		this.linea=linea;
	}
	
	public String getDesc() {
		return "(LINEA "+linea+") Los comentarios DEBEN llevar dos barras. Sólo se ha encontrado una.";
	}
	

}
