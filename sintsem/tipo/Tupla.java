package sintsem.tipo;

import java.util.LinkedList;

// Equivalente a t1xt2x...tn,
// siendo ti un tipo
public class Tupla {
	
	private LinkedList<Tipo> tipos = new LinkedList<>();

	private Tupla() {}
	
	public Tupla(Tipo t) { 
		this.tipos.addFirst(t);
	}
	
	// Dado un tipo t y una tupla con elementos t1xt2x...xtn
	// crea una nueva tupla con elementos txt1xt2x...xtn
	public Tupla(Tipo t,Tupla otraTupla) {
		this.tipos.addLast(t);
		for( Tipo tipo : otraTupla.tipos )
			this.tipos.addLast(tipo);		
	}
	
	public static Tupla vacia() { return new Tupla(); }
	
	public boolean estaVacia() { return this.tipos.size() == 0; }
	public int numElem() { return this.tipos.size(); }
	public Tipo get(int index) { return this.tipos.get(index); }
	
	@Override
	public boolean equals(Object otro) {
			
		if( !(otro instanceof Tupla) )
			return false;
		
		Tupla otraTupla = (Tupla) otro;
		
		if( this.tipos.size() != otraTupla.tipos.size() )
			return false;
		
		boolean iguales = true;
		for(int i=0;i<tipos.size() && iguales;i++) {
			if( !this.tipos.get(i).equals( otraTupla.tipos.get(i)) )
				iguales=false;
		}
		return iguales;
	}
	
	@Override
	public String toString() {
		
		if( this.estaVacia() )
			return "void";
		
		String res = "";
		for(int i=0;i<tipos.size()-1;i++)
			res+=tipos.get(i)+" x ";
		return res+tipos.get( tipos.size()-1 );
	}
}
