package tablasim.tabla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sintsem.tipo.Tipo;

// Estructura de datos que usamos como tabla de símbolos
public class Tabla {
	
	// Cada tabla cuando se crea tiene un
	// identificador numerico
	private int id;

	private int desplazamiento;
	
	// Esto para cuando se crea una tabla local
	private Tipo tipoRetornoEsperado;

	private Map<Integer,FilaTS> tab;

	public Tabla(int id) {
		this.id=id;
		this.desplazamiento = 0;
		tab = new HashMap<>();
	}

	public Tabla(int id, Tipo tipoRetornoEsperado) {
		this(id);
		this.tipoRetornoEsperado = tipoRetornoEsperado;
	}

	public void insert(FilaTS fila) {
		tab.put(fila.getID(), fila);
	}

	// Si tenemos el id de la fila podemos acceder a ella en o(1).
	public FilaTS getByPosTS(Integer posTS) {
		return tab.get(posTS);
	}

	// Pero si buscamos por lexema no queda otra que recorrer
	public FilaTS getByLex(String lexema) {
		FilaTS res=null;
		boolean found = false;
		Iterator<FilaTS> it = tab.values().iterator();

		while( it.hasNext() && !found ) {
			res = it.next();
			if( res.getLex().equals(lexema) )
				found=true;	
		}
		return ( found ? res : null );
	}

	@Override
	public String toString() {

		String s = "TABLA #"+id+":\n";

		for( FilaTS fila : tab.values() )
			s+=fila.toString()+"\n";

		return s + "\n------------------------------\n\n";
	}

	public void agregarTipoDesp(Integer posTS, Tipo t) {
		FilaTS fila = tab.get(posTS);
		
		fila.setTipo(t);
		fila.setDesp(desplazamiento);
		desplazamiento += t.tamano();
	}
	
	public void remove(Integer posTS) {
		tab.remove(posTS);
	}
	
	public Tipo getTipoRetornoEsperado() {
		return tipoRetornoEsperado;
	}

}
