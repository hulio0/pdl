package tablasim;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import control.Salida;
import sintsem.AnalizadorSintSem;
import sintsem.tipo.Tipo;


public class TablaS {

	private static class Tabla {

		// Cada tabla cuando se crea tiene un
		// identificador numerico
		private int id;

		private int desplazamiento;
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
				if( res.getLex().equals(lexema) ) {
					found=true;
				}
			}
			return ( found ? res : null );
		}

		@Override
		public String toString() {

			String s = "TABLA #"+id+":\n";

			for( FilaTS fila : tab.values() )
				s+=fila.toString()+"------------------------------\n";

			// El ultimo salto de linea es para que
			// las tablas no queden muy juntas
			return s + "\n";
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


	} // EOClaseTabla

	private static Salida salidaTS;
	private static Tabla global;
	private static Tabla local;

	private static Tabla actual;

	// Cada tabla tendra un identificador numerico
	private static int nextTable;

	// Iremos asignando a cada FILA que se cree un
	// identificador, ese identificador es el que
	// llevara cada token ID en su atributo (será
	// su posición en la tabla de símbolos correspondiente)
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
		// cree tendra tambien id 1
		nextTable = nextRow = 1;

		// Inicialmente, la tabla actual será la global
		actual = global;
	}

	public static void abrirAmbito(Tipo tipoRetornoEsperado) {
		local = new Tabla(nextTable++,tipoRetornoEsperado);
		actual = local;
	}

	public static void cerrarAmbito() {
		salidaTS.escribir(local.toString());
		local = null;
		actual = global;

		// OBS: La tabla de símbolos global
		// se escribe en el terminarEjecucion()
		// de esta clase
	}
	
	public static boolean estoyEnFuncion() {
		return actual == local;
	}

	// Inserta en una nueva fila el lexema del token ID que quiere añadir
	// el léxico. Este método devuelve el id que se le asigna a la nueva fila
	public static int insertar(String lex) {

		int idNuevaFila = nextRow++;
		FilaTS f = new FilaTS();
		  f.setID(idNuevaFila);
		  f.setLex(lex);

		actual.insert(f);
		return idNuevaFila;
	}
	
	
	// Usado por el léxico, solo busca en la TS actual
	// salvo que estemos en un ambito local, no lo hayamos
	// encontrado en la tabla local y NO estemos declarando
	public static Integer getPosTSLexico(String lex) {
		FilaTS res = null;
		res = actual.getByLex(lex);	
		
		// Solo 
		if(res==null && estoyEnFuncion() && !AnalizadorSintSem.estoyEnDeclaracion())
			res = global.getByLex(lex);
		
		return ( res != null ? res.getID() : null );
	}
	
	// Auxiliar para no repetir código de get. Busca
	// por posTS en todas las tablas que puede
	private static FilaTS getByPS(Integer posTS) {
		FilaTS res = null;
		res = actual.getByPosTS(posTS);
		if(res==null && estoyEnFuncion())
			res = global.getByPosTS(posTS);
		return res;
	}

	// Usado por el sintáctico, busca en todas las tablas que puede
	public static String getLexema(Integer posTS) {
		FilaTS res = null;
		res = getByPS(posTS);
		return ( res != null ? res.getLex() : null );
	}
	
	// Usado por el semántico. Es imposible que NO esté la
	// fila correspondiente. Lo que sí puede ocurrir es que
	// el tipo sea null (caso en el que intentamos usar un ID
	// que no ha sido declarado aún, en cuyo caso se interpreta
	// que es entero y global)
	public static Tipo getTipo(Integer posTS) {		
		FilaTS res = null;
		res = getByPS(posTS);
		if( res.getTipo() == null ) {
			
			if( estoyEnFuncion() ) {
				local.remove(posTS);
				global.insert(res); 
			}
			
			global.agregarTipoDesp(posTS, Tipo.entero());			
			return Tipo.entero();
		}
		
		return res.getTipo();	
 	}

	public static void agregarTipoDesp(Integer posTS,Tipo t) {	
		actual.agregarTipoDesp(posTS,t);
	}
	
	public static void agregarTipoFunc(Integer posTS,Tipo.Funcion tipoFun) {
		FilaTS fila = global.getByPosTS(posTS);
		fila.setTipo(tipoFun);
	}
	
	
	// Es imposible llamar a este método si no estamos en
	// un ámbito local (el sintáctico lo evitará)
	public static Tipo tipoRetornoEsperado() {
		return local.getTipoRetornoEsperado();
	}
	
	public static void terminarEjecucion() {
		// Si esto ocurre es que ha habido algun error
		// lexico, sintactico o semantico
		if(local!=null) {
			salidaTS.escribir(local.toString());
		}

		salidaTS.escribir(global.toString());
	}

}
