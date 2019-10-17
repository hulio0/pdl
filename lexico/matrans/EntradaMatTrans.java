package lexico.matrans;

// Clase que encapsula las entradas de la matriz de transicion del analizador
// lexico, que se componen por el estado al que se transita y la accion semantica
// que se ejecuta en dicha transicion
public class EntradaMatTrans {
	
	private Integer estado;
	private Accion accion;
	
	public EntradaMatTrans(Integer estado, Accion accion) {
		this.estado=estado;
		this.accion=accion;
	}
	
	public Integer estado() { return estado; }
	public Accion accion() { return accion; }
	
	
}
