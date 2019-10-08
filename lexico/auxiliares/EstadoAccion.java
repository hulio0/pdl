package lexico.auxiliares;

// Clase que encapsula las entradas de la matriz de transicion del analizador
// lexico, donde una de ellas es el estado al que se transita y la otra es la
// accion semantica que se ejecuta en dicha transicion
public class EstadoAccion {
	
	private Integer estado;
	private Accion accion;
	
	public EstadoAccion(Integer estado, Accion accion) {
		this.estado=estado;
		this.accion=accion;
	}
	
	public Integer estado() { return estado; }
	public Accion accion() { return accion; }
	
	
}
