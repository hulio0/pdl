package lexico.auxiliares;

// Clase que encapsula las entradas de la matriz de transición del analizador
// léxico, donde una de ellas es el estado al que transitamos y la otra es la
// acción semántica que ejecutamos en dicha transición
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
