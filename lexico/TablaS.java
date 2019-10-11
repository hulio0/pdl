package lexico;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import lexico.auxiliares.FilaTS;

public class TablaS {
			
	private static class Tabla {
		
		private int id;
		
		// Si tenemos el id de la fila podemos acceder a ella en o(1). 
		// Pero si buscamos por otro campo no queda otra que recorrer
		private Map<Integer,FilaTS> tab;
		
		public Tabla(int id) {
			this.id=id;
			tab = new HashMap<Integer,FilaTS>();
		}
		
		public int insert(FilaTS fila) {
			int idFila = fila.getID();
			  tab.put(idFila, fila);
			  
			return idFila;
		}
		
		public FilaTS get(String lexema) {
			FilaTS res=null;;
			boolean found = false;
			Iterator<FilaTS> it = tab.values().iterator();
			
			while( it.hasNext() && !found ) {
				res = it.next();
				found = res.getLex().equals(lexema);
			}
			
			return ( found ? res : null );
		}
		
	} // EOClaseTabla
	
	
	// Cada tabla tendra un identificador numérico
	private static int nextTable;
	
	// Iremos asignando a cada FILA que se cree un 
	// identificador, ese identificador es el que 
	// llevará cada token ID
	private static int nextRow;
	
	private static PositionList<Tabla> tablas;
					
	public static void iniciar() {
		nextRow=1;
		nextTable=1;
		tablas = new NodePositionList<Tabla>();
		
		// Creamos la tabla global:
		abrirAmbito();
	}
	
	public static void abrirAmbito() {
		tablas.addLast( new Tabla(nextTable++) );
	}
	
	public static void cerrarAmbito() {
		// To-Do escribir la tabla en un fichero antes de destruirla
		tablas.remove( tablas.last() );
	}

	// Devuelve el id de la fila en la que se inserta.
	// Este método lo utiliza el A. Léx
	public static int insert(String lex) {
		
		int idNuevaFila = nextRow++;
		
		FilaTS f = new FilaTS();
		  f.setID(idNuevaFila);
		  f.setLex(lex);
		
		// La última tabla de la PositionList es la actual.
		// .last devuelve un Position<Tabla>, por ello el .element
		tablas.last().element().insert(f);		
		return idNuevaFila;
	}
	
	// Devuelve el id de la fila en la que está ubicado lex, teniendo
	// prioridad la de ÁMBITO MÁS CERCANO. Si no hay ninguna fila con
	// ese lexema en ninguna table entonces se devuelve null
	public static Integer get(String lex) {
		
		FilaTS res=null;;
		
		Position<Tabla> tablaActual = tablas.last(); 		
		while( tablaActual!=null && (res=tablaActual.element().get(lex))==null )
			tablaActual=tablas.prev(tablaActual);
		
		return ( res!=null ? res.getID() : null );
	}
	
	// Solo busca en la tabla actual (la que representa el ámbito actual)
	public static Integer currentScopeGet(String lex) {
		FilaTS res = tablas.last().element().get(lex);
		return ( res!=null ? res.getID() : null );
	}
	
}
