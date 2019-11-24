package tablasim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import control.Salida;


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
		
		// Si tenemos el id el acceso es inmediato
		public FilaTS get(int codID) {
			return tab.get(codID);
		}
		
		public String toString() {
			
			String s = "TABLA #"+id+":\n";
			
			for( FilaTS fila : tab.values() ) {
				s+="*'"+fila.getLex()+"'\n";
				s+="+id:"+fila.getID()+"\n";
			}
			return s;
		}
		
		
	} // EOClaseTabla
	
	private static Salida salidaTS;
	private static Tabla global;
	private static Tabla local;
	
	// Cada tabla tendra un identificador numerico
	private static int nextTable;
	
	// Iremos asignando a cada FILA que se cree un 
	// identificador, ese identificador es el que 
	// llevara cada token ID en su atributo
	private static int nextRow;
	
	public static void iniciar(File ficheroTS) {
		salidaTS = new Salida(ficheroTS);
		global = new Tabla(0);
		local = null;
		nextTable = nextRow = 1;
	}
		
	public static void abrirAmbito() {
		local = new Tabla(nextTable++);		
	}
	
	public static void cerrarAmbito() {
		
		if( local!=null ) {
			salidaTS.escribir( local.toString() + "\n" );
			local = null;
		}
		
		else {
			salidaTS.escribir( global.toString() + "\n" );
			global = null;
		}
	}

	// Inserta en una nueva fila el lexema del token ID que quiere añadir
	// el léxico. Este método devuelve el id que se le asigna a la nueva fila
	public static int insert(String lex) {

		int idNuevaFila = nextRow++;
		FilaTS f = new FilaTS();
		  f.setID(idNuevaFila);
		  f.setLex(lex);
		
		
		// Si local está inicializada entonces es la tabla actual
		if( local != null )
			local.insert(f);
		
		// En caso contrario, la tabla actual es la global
		else
			global.insert(f);
		
		
		return idNuevaFila;
	}
	
	
	// Devuelve el id de la fila en la que esta ubicado lex, teniendo
	// prioridad la de AMBITO MAS CERCANO. Si no hay ninguna fila con
	// ese lexema en ninguna tabla entonces se devuelve null
	public static Integer get(String lex) {
		
		FilaTS res=null;
		
		// Si estamos en un ámbito local buscamos allí
		if( local!=null )
			res = local.get(lex);
		
		// Si no lo encontramos en el local o estamos en un
		// ámbito global pues buscamos en la tabla global
		if( res == null )
			res = global.get(lex);
				
		return ( res != null ? res.getID() : null );
	}
	
	// Igual se podría compactar más
	public static String get(int codID) {
		FilaTS res=null;
		
		if( local!=null )
			res = local.get(codID);
		
		if( res == null )
			res = global.get(codID);
				
		return ( res != null ? res.getLex() : null );
	}
}
