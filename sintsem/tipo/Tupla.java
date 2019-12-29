package sintsem.tipo;

import java.util.LinkedList;

// Equivalente a t1xt2x...tn,
// siendo ti un tipo
public class Tupla {
	
	private LinkedList<Tipo> tipos;

	private Tupla() {}
	
	public Tupla(Tipo t) {
		this.tipos = new LinkedList<>();
		this.tipos.addFirst(t);
	}
	
	// Dado un tipo t y una tupla con elementos t1xt2x...xtn
	// crea una nueva tupla con elementos txt1xt2x...xtn
	public Tupla(Tipo t,Tupla otraTupla) {
		this.tipos = new LinkedList<>();
		this.tipos.addLast(t);
		for( Tipo tipo : otraTupla.tipos )
			this.tipos.addLast(tipo);		
	}
	
	public static Tupla vacia() { return new Tupla(); }
	public boolean estaVacia() { return tipos == null; }
	
}
