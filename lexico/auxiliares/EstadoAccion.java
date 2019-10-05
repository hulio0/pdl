package lexico.auxiliares;

// Clase que encapsula las entradas de la matriz de transici�n del analizador
// l�xico, donde una de ellas es el estado al que transitamos y la otra es la
// acci�n sem�ntica que ejecutamos en dicha transici�n
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
