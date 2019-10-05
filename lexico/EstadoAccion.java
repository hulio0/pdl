package lexico;

public class EstadoAccion {
	
	private Integer estado;
	private Accion accion;
	
	public EstadoAccion(Integer estado, Accion action) {
		this.estado=estado;
		this.accion=accion;
	}
	
	public Integer estado() { return estado; }
	public Accion accion() { return accion; }
	
	
}
