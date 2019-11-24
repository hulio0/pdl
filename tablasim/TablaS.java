package tablasim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import control.Salida;


public class TablaS {
			
	private static class Tabla {	
		
		// Cada tabla cuando se crea tiene un
		// identificaor numerico
		private int id;
		
		private Map<Integer,FilaTS> tab;
		
		public Tabla(int id) {			
			this.id=id;
			tab = new HashMap<Integer,FilaTS>();
		}
		
		public void insert(FilaTS fila) {
			tab.put(fila.getID(), fila);
		}
		
		// Si llega un Integer estamos buscando por id de fila (inmediato)
		// y si llega un String estamos buscando por otro campo (toca recorrer)
		public FilaTS get(Object aBuscar) {
			if( aBuscar instanceof Integer )
				return getByRowID( (Integer) aBuscar );
			else if( aBuscar instanceof String )
				return getByLex( (String) aBuscar );
			
			// Esto nunca pasará porque solo hacemos public
			// metodos de búsqueda que reciben Integer o bien String
			else
				throw new IllegalArgumentException();
		}
		
		// Si tenemos el id de la fila podemos acceder a ella en o(1). 
		private FilaTS getByRowID(Integer codFila) {
			return tab.get(codFila);
		}
		
		// Pero si buscamos por otro campo no queda otra que recorrer
		private FilaTS getByLex(String lexema) {
			FilaTS res=null;;
			boolean found = false;
			Iterator<FilaTS> it = tab.values().iterator();
			
			while( it.hasNext() && !found ) {
				res = it.next();
				if( res.getLex().equals(lexema) ) found=true;
			}
			return ( found ? res : null );
		}
		
		// TO-DO: Completar cuando se añadan el
		// resto de columnas a FilaTS
		public String toString() {
			
			String s = "TABLA #"+id+":\n";
			
			for( FilaTS fila : tab.values() ) {
				s+="*'"+fila.getLex()+"'\n";
				s+="+id:"+fila.getID()+"\n";
			}
			
			// El ultimo salto de linea es para que 
			// las tablas no queden muy juntas
			return s + "\n";
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
		
		// La tabla global siempre tiene el id 0
		// (pues es la primera que se crea)
		global = new Tabla(0);
		
		// Inicialmente, lógicamente no hay tabla local
		local = null;
		
		// La siguiente tabla que se cree tendrá id 1 y,
		// por decisión de diseño, la primera fila que se
		// cree tendra id 1
		nextTable = nextRow = 1;
	}
		
	public static void abrirAmbito() {
		local = new Tabla(nextTable++);		
	}
	
	public static void cerrarAmbito() {
		
		if( local!=null ) {
			salidaTS.escribir(local.toString());
			local = null;
		}
		
		// Esto nunca pasara, pq el resto de modulos
		// no lo permitiran. De hecho, cerrarAmbito solo
		// se llama despues de haber abierto uno
		else 
			System.out.println("¡ERROR TS!");
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
	
	private static FilaTS get(Object aBuscar) {
		FilaTS res = null;
		
		// Si estamos en un ámbito local buscamos allí
		if( local!=null )
			res = local.get(aBuscar);
		
		// Si no lo encontramos en el local o si directamente no
		// hay ambito local entonces buscamos en la tabla global
		if( res == null )
			res = global.get(aBuscar);
				
		return res;
	}
	
	
	// Metodo usado por el A. lexico. Devolvemos el codFila
	// asociado a lex si esta en la TablaS y null e.o.c
	public static Integer getLexico(String lex) {
		FilaTS res = null;
		  res = get( lex );
		  
		return ( res != null ? res.getID() : null );
	}
	
	// Metodo usado por el A. sintactico. Devolvemos el lexema
	// de la fila correspondiente a codFila
	public static String getSintactico(Integer codFila) {
		FilaTS res = null;
		  res = get( codFila );
		  
		// No es necesaria esta comprobación en teoria pq el 
		// sintactico solo busca cosas que están ya en la tabla
		return ( res != null ? res.getLex() : null );
	}
	
	public static void terminarEjecucion() {
		// Si esto ocurre es que ha habido algun error
		// lexico, sintactico o semantico
		if(local!=null) 
			salidaTS.escribir(local.toString());
		
		salidaTS.escribir(global.toString());
	}	
}
